package job_recommendation_backend.repository;

import job_recommendation_backend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserId(Long userId);

    boolean existsByUserIdAndJobId(Long userId, Long jobId);
}