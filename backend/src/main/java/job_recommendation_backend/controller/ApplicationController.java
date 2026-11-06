package job_recommendation_backend.controller;

import job_recommendation_backend.dto.ApplicationResponse;
import job_recommendation_backend.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/{jobId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse applyForJob(
            @PathVariable Long userId,
            @PathVariable Long jobId) {

        return applicationService.applyForJob(userId, jobId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationResponse> getUserApplications(
            @PathVariable Long userId) {

        return applicationService.getUserApplications(userId);
    }
}