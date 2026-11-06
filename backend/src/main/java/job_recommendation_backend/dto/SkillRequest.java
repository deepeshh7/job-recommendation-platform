package job_recommendation_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class SkillRequest {

    @NotBlank
    private String name;

    public SkillRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}