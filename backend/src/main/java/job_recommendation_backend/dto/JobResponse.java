package job_recommendation_backend.dto;

import job_recommendation_backend.entity.Job;

public class JobResponse {

    private Long id;
    private String title;
    private String company;
    private String location;
    private String description;
    private String employmentType;
    private Integer minExperience;

    public JobResponse(
            Long id,
            String title,
            String company,
            String location,
            String description,
            String employmentType,
            Integer minExperience
    ) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
        this.employmentType = employmentType;
        this.minExperience = minExperience;
    }

    public static JobResponse fromJob(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getLocation(),
                job.getDescription(),
                job.getEmploymentType(),
                job.getMinExperience()
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public Integer getMinExperience() {
        return minExperience;
    }
}