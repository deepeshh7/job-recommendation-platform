package job_recommendation_backend.controller;

import jakarta.validation.Valid;
import job_recommendation_backend.dto.UserRequest;
import job_recommendation_backend.dto.UserResponse;
import job_recommendation_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(
            @Valid @RequestBody UserRequest request) {

        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(
            @PathVariable Long id) {

        return userService.getUserById(id);
    }
}