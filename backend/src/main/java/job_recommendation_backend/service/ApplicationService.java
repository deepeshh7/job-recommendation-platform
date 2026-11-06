package job_recommendation_backend.service;

import job_recommendation_backend.dto.ApplicationResponse;
import job_recommendation_backend.dto.ApplicationStatusRequest;
import job_recommendation_backend.entity.Application;
import job_recommendation_backend.entity.Job;
import job_recommendation_backend.entity.User;
import job_recommendation_backend.exception.ConflictException;
import job_recommendation_backend.exception.ResourceNotFoundException;
import job_recommendation_backend.repository.ApplicationRepository;
import job_recommendation_backend.repository.JobRepository;
import job_recommendation_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            UserRepository userRepository,
            JobRepository jobRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public ApplicationResponse applyForJob(
            Long userId,
            Long jobId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        if (applicationRepository.existsByUserIdAndJobId(
                userId,
                jobId
        )) {
            throw new ConflictException(
                    "User has already applied for this job"
            );
        }

        Application application = new Application(
                user,
                job,
                "APPLIED",
                LocalDateTime.now()
        );

        Application savedApplication =
                applicationRepository.save(application);

        return ApplicationResponse.fromApplication(
                savedApplication
        );
    }

    public List<ApplicationResponse> getUserApplications(
            Long userId
    ) {

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return applicationRepository.findByUserId(userId)
                .stream()
                .map(ApplicationResponse::fromApplication)
                .toList();
    }

    public ApplicationResponse updateApplicationStatus(
            Long applicationId,
            ApplicationStatusRequest request
    ) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Application not found"));

        application.setStatus(request.getStatus());

        Application updatedApplication =
                applicationRepository.save(application);

        return ApplicationResponse.fromApplication(updatedApplication);
    }
}