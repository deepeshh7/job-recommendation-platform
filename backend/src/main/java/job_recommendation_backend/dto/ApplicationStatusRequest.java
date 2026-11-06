package job_recommendation_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class ApplicationStatusRequest {

    @NotBlank
    private String status;

    public ApplicationStatusRequest() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}