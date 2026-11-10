
package job_recommendation_backend.repository;

import job_recommendation_backend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobRepository extends JpaRepository<Job, Long> {

    // Search jobs by title, company or description
    @Query("""
            SELECT j FROM Job j
            WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Job> searchJobs(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    // Filter jobs by location, employment type and experience
    @Query("""
            SELECT j FROM Job j
            WHERE (:location IS NULL
                   OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))
              AND (:employmentType IS NULL
                   OR LOWER(j.employmentType) = LOWER(:employmentType))
              AND (:maxExperience IS NULL
                   OR j.minExperience <= :maxExperience)
            """)
    Page<Job> filterJobs(
            @Param("location") String location,
            @Param("employmentType") String employmentType,
            @Param("maxExperience") Integer maxExperience,
            Pageable pageable
    );
}
