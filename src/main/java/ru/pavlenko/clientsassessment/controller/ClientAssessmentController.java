package ru.pavlenko.clientsassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pavlenko.clientsassessment.dto.AssessmentOutputDTO;
import ru.pavlenko.clientsassessment.dto.ClientInputDTO;
import ru.pavlenko.clientsassessment.dto.ErrorResponse;
import ru.pavlenko.clientsassessment.service.ClientAssessmentService;

@RestController
@RequestMapping("/api/assessment")
public class ClientAssessmentController {
    @Autowired
    private ClientAssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<Object> assessClient(@RequestBody ClientInputDTO input) {
        AssessmentOutputDTO output = null;
        try {
            output = assessmentService.assessClient(input);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Внутренняя ошибка: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}
