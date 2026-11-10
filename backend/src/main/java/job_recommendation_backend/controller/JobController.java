
package job_recommendation_backend.controller;

import job_recommendation_backend.dto.JobRequest;
import job_recommendation_backend.dto.JobResponse;
import job_recommendation_backend.service.JobService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Create a job
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponse createJob(@Valid @RequestBody JobRequest request) {
        return jobService.createJob(request);
    }

    // Get a job by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public JobResponse getJob(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    // Get all jobs with pagination and sorting
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<JobResponse> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return jobService.getAllJobs(
                page, size, sortBy, direction
        );
    }

    // Search jobs with pagination and sorting
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<JobResponse> searchJobs(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return jobService.searchJobs(
                keyword, page, size, sortBy, direction
        );
    }

    // Filter jobs with pagination and sorting
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public Page<JobResponse> filterJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String employmentType,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return jobService.filterJobs(
                location,
                employmentType,
                maxExperience,
                page,
                size,
                sortBy,
                direction
        );
    }

    // Update a job
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public JobResponse updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request
    ) {
        return jobService.updateJob(id, request);
    }

    // Delete a job
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }
}
