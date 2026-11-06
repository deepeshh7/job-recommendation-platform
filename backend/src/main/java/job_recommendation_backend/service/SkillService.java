package job_recommendation_backend.service;

import job_recommendation_backend.dto.SkillRequest;
import job_recommendation_backend.dto.SkillResponse;
import job_recommendation_backend.entity.Skill;
import job_recommendation_backend.entity.User;
import job_recommendation_backend.exception.ResourceNotFoundException;
import job_recommendation_backend.repository.SkillRepository;
import job_recommendation_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public SkillService(
            SkillRepository skillRepository,
            UserRepository userRepository
    ) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }

    public SkillResponse addSkillToUser(
            Long userId,
            SkillRequest request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Skill skill = skillRepository.findByName(request.getName())
                .orElseGet(() ->
                        skillRepository.save(
                                new Skill(request.getName())
                        )
                );

        user.getSkills().add(skill);

        userRepository.save(user);

        return SkillResponse.fromSkill(skill);
    }

    public List<SkillResponse> getUserSkills(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return user.getSkills()
                .stream()
                .map(SkillResponse::fromSkill)
                .toList();
    }

    public void removeSkillFromUser(
            Long userId,
            Long skillId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Skill not found"));

        user.getSkills().remove(skill);

        userRepository.save(user);
    }
}