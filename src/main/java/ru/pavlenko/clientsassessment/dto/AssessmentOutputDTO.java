package ru.pavlenko.clientsassessment.dto;

import ru.pavlenko.clientsassessment.model.ClientAssessment;

public class AssessmentOutputDTO {
    private boolean success;
    private String details;

    public AssessmentOutputDTO(ClientAssessment assessment) {
        this.success = assessment.isSuccess();
        this.details = assessment.getDetails();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
