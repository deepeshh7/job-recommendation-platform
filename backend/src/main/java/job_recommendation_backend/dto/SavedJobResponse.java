package job_recommendation_backend.dto;

import job_recommendation_backend.entity.SavedJob;

public class SavedJobResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private String company;
    private String location;

    public SavedJobResponse(
            Long id,
            Long jobId,
            String jobTitle,
            String company,
            String location) {

        this.id = id;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.location = location;
    }

    public static SavedJobResponse fromSavedJob(SavedJob savedJob) {

        return new SavedJobResponse(
                savedJob.getId(),
                savedJob.getJob().getId(),
                savedJob.getJob().getTitle(),
                savedJob.getJob().getCompany(),
                savedJob.getJob().getLocation()
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

    public String getLocation() {
        return location;
    }
}