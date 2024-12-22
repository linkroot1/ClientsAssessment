package ru.pavlenko.clientsassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pavlenko.clientsassessment.model.ClientAssessment;

public interface ClientAssessmentRepository extends JpaRepository<ClientAssessment, Long> {
}
