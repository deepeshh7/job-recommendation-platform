package job_recommendation_backend.controller;

import jakarta.validation.Valid;
import job_recommendation_backend.dto.JobRequest;
import job_recommendation_backend.dto.JobResponse;
import job_recommendation_backend.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponse createJob(
            @Valid @RequestBody JobRequest request) {

        return jobService.createJob(request);
    }

@GetMapping("/search")
@ResponseStatus(HttpStatus.OK)
public List<JobResponse> searchJobs(
        @RequestParam String keyword) {

    return jobService.searchJobs(keyword);
}

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public JobResponse getJob(
            @PathVariable Long id) {

        return jobService.getJobById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<JobResponse> getAllJobs() {

        return jobService.getAllJobs();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public JobResponse updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request) {

        return jobService.updateJob(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(
            @PathVariable Long id) {

        jobService.deleteJob(id);
    }

@GetMapping("/filter")
@ResponseStatus(HttpStatus.OK)
public List<JobResponse> filterJobs(
        @RequestParam(required = false) String location,
        @RequestParam(required = false) String employmentType,
        @RequestParam(required = false) Integer maxExperience
) {
    return jobService.filterJobs(location, employmentType, maxExperience);
}

}