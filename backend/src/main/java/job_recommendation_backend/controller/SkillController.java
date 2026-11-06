package job_recommendation_backend.controller;

import jakarta.validation.Valid;
import job_recommendation_backend.dto.SkillRequest;
import job_recommendation_backend.dto.SkillResponse;
import job_recommendation_backend.service.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SkillResponse addSkill(
            @PathVariable Long userId,
            @Valid @RequestBody SkillRequest request) {

        return skillService.addSkillToUser(userId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SkillResponse> getUserSkills(
            @PathVariable Long userId) {

        return skillService.getUserSkills(userId);
    }

    @DeleteMapping("/{skillId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSkill(
            @PathVariable Long userId,
            @PathVariable Long skillId) {

        skillService.removeSkillFromUser(userId, skillId);
    }
}