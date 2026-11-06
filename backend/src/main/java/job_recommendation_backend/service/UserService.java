package job_recommendation_backend.service;

import job_recommendation_backend.dto.UserRequest;
import job_recommendation_backend.dto.UserResponse;
import job_recommendation_backend.entity.User;
import job_recommendation_backend.exception.ConflictException;
import job_recommendation_backend.exception.ResourceNotFoundException;
import job_recommendation_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ConflictException("Email already exists");
        }

        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        User savedUser = userRepository.save(user);

        return UserResponse.fromUser(savedUser);
    }

    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return UserResponse.fromUser(user);
    }
}