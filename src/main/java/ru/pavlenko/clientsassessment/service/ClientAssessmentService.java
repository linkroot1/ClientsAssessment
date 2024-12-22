package ru.pavlenko.clientsassessment.service;

import ru.pavlenko.clientsassessment.dto.AssessmentOutputDTO;
import ru.pavlenko.clientsassessment.dto.ClientInputDTO;

public interface ClientAssessmentService {
    AssessmentOutputDTO assessClient(ClientInputDTO input) throws Exception;
}
