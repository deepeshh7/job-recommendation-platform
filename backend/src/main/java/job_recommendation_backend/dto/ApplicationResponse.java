package job_recommendation_backend.dto;

import job_recommendation_backend.entity.Application;

import java.time.LocalDateTime;

public class ApplicationResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private String company;
    private String status;
    private LocalDateTime appliedAt;

    public ApplicationResponse(
            Long id,
            Long jobId,
            String jobTitle,
            String company,
            String status,
            LocalDateTime appliedAt
    ) {
        this.id = id;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public static ApplicationResponse fromApplication(
            Application application
    ) {

        return new ApplicationResponse(
                application.getId(),
                application.getJob().getId(),
                application.getJob().getTitle(),
                application.getJob().getCompany(),
                application.getStatus(),
                application.getAppliedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
}