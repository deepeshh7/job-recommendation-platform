
package job_recommendation_backend.service;

import job_recommendation_backend.dto.JobRequest;
import job_recommendation_backend.dto.JobResponse;
import job_recommendation_backend.entity.Job;
import job_recommendation_backend.exception.ResourceNotFoundException;
import job_recommendation_backend.repository.JobRepository;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Create a job
    public JobResponse createJob(JobRequest request) {

        Job job = new Job(
                request.getTitle(),
                request.getCompany(),
                request.getLocation(),
                request.getDescription(),
                request.getEmploymentType(),
                request.getMinExperience()
        );

        Job savedJob = jobRepository.save(job);

        return JobResponse.fromJob(savedJob);
    }

    // Get a job by ID
    public JobResponse getJobById(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        return JobResponse.fromJob(job);
    }

    // Get all jobs with pagination and sorting
    public Page<JobResponse> getAllJobs(
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Pageable pageable = createPageable(page, size, sortBy, direction);

        return jobRepository.findAll(pageable)
                .map(JobResponse::fromJob);
    }

    // Update a job
    public JobResponse updateJob(Long id, JobRequest request) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setDescription(request.getDescription());
        job.setEmploymentType(request.getEmploymentType());
        job.setMinExperience(request.getMinExperience());

        Job updatedJob = jobRepository.save(job);

        return JobResponse.fromJob(updatedJob);
    }

    // Delete a job
    public void deleteJob(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        jobRepository.delete(job);
    }

    // Search jobs with pagination and sorting
    public Page<JobResponse> searchJobs(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        if (!StringUtils.hasText(keyword)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Search keyword cannot be empty"
            );
        }

        Pageable pageable = createPageable(page, size, sortBy, direction);

        return jobRepository.searchJobs(keyword.trim(), pageable)
                .map(JobResponse::fromJob);
    }

    // Filter jobs with pagination and sorting
    public Page<JobResponse> filterJobs(
            String location,
            String employmentType,
            Integer maxExperience,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        if (maxExperience != null && maxExperience < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Maximum experience cannot be negative"
            );
        }

        location = normalizeFilter(location);
        employmentType = normalizeFilter(employmentType);

        Pageable pageable = createPageable(page, size, sortBy, direction);

        return jobRepository.filterJobs(
                location,
                employmentType,
                maxExperience,
                pageable
        ).map(JobResponse::fromJob);
    }

    // Convert blank optional filters into null
    private String normalizeFilter(String value) {

        return StringUtils.hasText(value) ? value.trim() : null;
    }

    // Validate pagination and sorting parameters
    private Pageable createPageable(
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Set<String> allowedFields = Set.of(
                "id",
                "title",
                "company",
                "location",
                "employmentType",
                "minExperience"
        );

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page must be non-negative and size must be between 1 and 100"
            );
        }

        if (!allowedFields.contains(sortBy)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid sort field"
            );
        }

        Sort.Direction sortDirection;

        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Direction must be asc or desc"
            );
        }

        return PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, sortBy)
        );
    }
}
