package ru.pavlenko.clientsassessment.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.pavlenko.clientsassessment.dto.AssessmentOutputDTO;
import ru.pavlenko.clientsassessment.dto.ClientInputDTO;
import ru.pavlenko.clientsassessment.model.ClientAssessment;
import ru.pavlenko.clientsassessment.repository.ClientAssessmentRepository;
import ru.pavlenko.clientsassessment.service.ClientAssessmentService;

import java.util.Map;

@Service
public class ClientAssessmentServiceImpl implements ClientAssessmentService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${camunda.base.url}")
    private String camundaBaseUrl;

    @Autowired
    private ClientAssessmentRepository clientAssessmentRepository;

    @Override
    public AssessmentOutputDTO assessClient(ClientInputDTO input) throws Exception {
        if (input == null) {
            throw new Exception("Ошибка входных данных: Входные данные должны быть заполнены");
        } else if (input.getInn() == null) {
            throw new Exception("Ошибка входных данных: ИНН компании должен быть заполнен");
        } else if (!input.getInn().chars().allMatch(Character::isDigit)) {
            throw new Exception("Ошибка входных данных: ИНН компании должен состоять из цифр");
        } else if (input.getCapital() == null) {
            throw new Exception("Ошибка входных данных: Капитал компании должен быть заполнен");
        } else if (input.getRegion() == null) {
            throw new Exception("Ошибка входных данных: Регион компании должен быть заполнен");
        } else if (!input.getRegion().chars().allMatch(Character::isDigit)) {
            throw new Exception("Ошибка входных данных: Регион компании должен состоять из цифр");
        }

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(getFullUrl());
            httpPost.setHeader("Content-Type", "application/json");

            String jsonVariables = null;
            StringEntity entity = null;
            try {
                jsonVariables = prepareRequest(input);
                entity = new StringEntity(jsonVariables);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            httpPost.setEntity(entity);

            // Отправляем запрос
            ClientAssessment clientAssessment = null;
            try (CloseableHttpResponse response = client.execute(httpPost)) {
                // Собираем ответ
                String jsonResponse = null;
                try {
                    jsonResponse = EntityUtils.toString(response.getEntity());
                    clientAssessment = transform(jsonResponse);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }

            clientAssessmentRepository.save(clientAssessment);
            return new AssessmentOutputDTO(clientAssessment);
        }
    }

    private String prepareRequest(ClientInputDTO clientInput) throws Exception {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        ObjectNode variablesNode = objectMapper.createObjectNode();

        variablesNode.putObject("inn").put("value", clientInput.getInn());
        variablesNode.putObject("capital").put("value", clientInput.getCapital());
        variablesNode.putObject("region").put("value", clientInput.getRegion());

        jsonNode.set("variables", variablesNode);
        jsonNode.put("withVariablesInReturn", true);

        return objectMapper.writeValueAsString(jsonNode);
    }

    private ClientAssessment transform(String jsonString) throws Exception {
        Map jsonMap = objectMapper.readValue(jsonString, Map.class);

        Map variables = (Map) jsonMap.get("variables");
        String inn = (String) ((Map) variables.get("inn")).get("value");
        boolean success = (Boolean) ((Map) variables.get("success")).get("value");
        String details = (String) ((Map) variables.get("details")).get("value");

        return new ClientAssessment(success, details, inn);
    }

    private String getFullUrl() {
        return camundaBaseUrl + "/engine-rest/process-definition/key/assessmentClientProcess/start";
    }
}
