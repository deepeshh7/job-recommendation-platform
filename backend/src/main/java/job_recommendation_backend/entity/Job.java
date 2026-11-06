package job_recommendation_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String employmentType;

    @Column(nullable = false)
    private Integer minExperience;

    public Job() {
    }

    public Job(
            String title,
            String company,
            String location,
            String description,
            String employmentType,
            Integer minExperience
    ) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
        this.employmentType = employmentType;
        this.minExperience = minExperience;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public void setMinExperience(Integer minExperience) {
        this.minExperience = minExperience;
    }
}