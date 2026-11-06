package job_recommendation_backend.dto;

import job_recommendation_backend.entity.Skill;

public class SkillResponse {

    private Long id;
    private String name;

    public SkillResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SkillResponse fromSkill(Skill skill) {
        return new SkillResponse(
                skill.getId(),
                skill.getName()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}