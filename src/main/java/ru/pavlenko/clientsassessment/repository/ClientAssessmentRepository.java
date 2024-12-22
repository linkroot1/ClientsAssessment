package ru.pavlenko.clientsassessment.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.pavlenko.clientsassessment.model.ClientAssessment;

public interface ClientAssessmentRepository extends ElasticsearchRepository<ClientAssessment, Long> {
}
