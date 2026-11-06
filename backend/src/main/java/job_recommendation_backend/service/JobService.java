package job_recommendation_backend.service;

import job_recommendation_backend.dto.JobRequest;
import job_recommendation_backend.dto.JobResponse;
import job_recommendation_backend.entity.Job;
import job_recommendation_backend.exception.ResourceNotFoundException;
import job_recommendation_backend.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobResponse createJob(JobRequest request) {

        Job job = new Job(
                request.getTitle(),
                request.getCompany(),
                request.getLocation(),
                request.getDescription(),
                request.getEmploymentType(),
                request.getMinExperience()
        );

        Job savedJob = jobRepository.save(job);

        return JobResponse.fromJob(savedJob);
    }

    public JobResponse getJobById(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        return JobResponse.fromJob(job);
    }

    public List<JobResponse> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(JobResponse::fromJob)
                .toList();
    }

    public JobResponse updateJob(Long id, JobRequest request) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setDescription(request.getDescription());
        job.setEmploymentType(request.getEmploymentType());
        job.setMinExperience(request.getMinExperience());

        Job updatedJob = jobRepository.save(job);

        return JobResponse.fromJob(updatedJob);
    }

    public void deleteJob(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        jobRepository.delete(job);
    }

public List<JobResponse> searchJobs(String keyword) {

    return jobRepository.searchJobs(keyword)
            .stream()
            .map(JobResponse::fromJob)
            .toList();
}

public List<JobResponse> filterJobs(
        String location,
        String employmentType,
        Integer maxExperience
) {
    return jobRepository.filterJobs(location, employmentType, maxExperience)
            .stream()
            .map(JobResponse::fromJob)
            .toList();
}

}