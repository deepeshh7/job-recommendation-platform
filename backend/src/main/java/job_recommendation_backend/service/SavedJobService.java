package job_recommendation_backend.service;

import job_recommendation_backend.dto.SavedJobResponse;
import job_recommendation_backend.entity.Job;
import job_recommendation_backend.entity.SavedJob;
import job_recommendation_backend.entity.User;
import job_recommendation_backend.repository.JobRepository;
import job_recommendation_backend.repository.SavedJobRepository;
import job_recommendation_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import job_recommendation_backend.exception.ConflictException;
import job_recommendation_backend.exception.ResourceNotFoundException;

@Service
public class SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public SavedJobService(
            SavedJobRepository savedJobRepository,
            UserRepository userRepository,
            JobRepository jobRepository) {

        this.savedJobRepository = savedJobRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public SavedJobResponse saveJob(Long userId, Long jobId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (savedJobRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new ConflictException("Job already saved");
        }

        SavedJob savedJob = new SavedJob(user, job);

        SavedJob saved = savedJobRepository.save(savedJob);

        return SavedJobResponse.fromSavedJob(saved);
    }

    public List<SavedJobResponse> getSavedJobs(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return savedJobRepository.findByUserId(userId)
                .stream()
                .map(SavedJobResponse::fromSavedJob)
                .toList();
    }

@Transactional
public void removeSavedJob(Long userId, Long jobId)  {

        if (!savedJobRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new ResourceNotFoundException("Saved job not found");
        }

        savedJobRepository.deleteByUserIdAndJobId(userId, jobId);
    }
}