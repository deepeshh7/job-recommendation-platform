package job_recommendation_backend.controller;

import job_recommendation_backend.dto.SavedJobResponse;
import job_recommendation_backend.service.SavedJobService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/saved-jobs")
public class SavedJobController {

    private final SavedJobService savedJobService;

    public SavedJobController(SavedJobService savedJobService) {
        this.savedJobService = savedJobService;
    }

    @PostMapping("/{jobId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SavedJobResponse saveJob(
            @PathVariable Long userId,
            @PathVariable Long jobId) {

        return savedJobService.saveJob(userId, jobId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SavedJobResponse> getSavedJobs(
            @PathVariable Long userId) {

        return savedJobService.getSavedJobs(userId);
    }

    @DeleteMapping("/{jobId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSavedJob(
            @PathVariable Long userId,
            @PathVariable Long jobId) {

        savedJobService.removeSavedJob(userId, jobId);
    }
}