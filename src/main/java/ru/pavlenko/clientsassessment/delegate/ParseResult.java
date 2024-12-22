package ru.pavlenko.clientsassessment.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParseResult implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        List<Map<String, Object>> result = (List<Map<String, Object>>) delegateExecution.getVariable("result");

        boolean success = !result.stream()
                .map(map -> (Boolean) map.get("isSuccess"))
                .reduce(true, (a, b) -> a && b);

        String details = result.stream()
                .map(map -> (String) map.get("details"))
                .filter(detail -> detail != null && !detail.isEmpty())
                .collect(Collectors.joining(", "));

        if (details.isEmpty()) {
            details = "Успешный скоринг";
        }

        delegateExecution.setVariable("success", success);
        delegateExecution.setVariable("details", details);
    }
}
