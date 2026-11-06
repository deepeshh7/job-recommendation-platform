package job_recommendation_backend.controller;

import jakarta.validation.Valid;
import job_recommendation_backend.dto.ApplicationResponse;
import job_recommendation_backend.dto.ApplicationStatusRequest;
import job_recommendation_backend.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ApplicationStatusController {

    private final ApplicationService applicationService;

    public ApplicationStatusController(
            ApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    @PutMapping("/{applicationId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse updateApplicationStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationStatusRequest request) {

        return applicationService.updateApplicationStatus(
                applicationId,
                request
        );
    }
}