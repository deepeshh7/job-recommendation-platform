
package job_recommendation_backend.exception;

import job_recommendation_backend.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleResourceNotFound(
            ResourceNotFoundException exception) {

        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                exception.getMessage()
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ErrorResponse handleConflict(
            ConflictException exception) {

        return new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidation(
            MethodArgumentNotValidException exception) {

        Map<String, String> fieldErrors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Validation failed",
                fieldErrors
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponse handleResponseStatus(
            ResponseStatusException exception) {

        int status = exception.getStatusCode().value();

        HttpStatus httpStatus = HttpStatus.resolve(status);

        String error = httpStatus != null
                ? httpStatus.getReasonPhrase()
                : "Request Error";

        String message = exception.getReason() != null
                ? exception.getReason()
                : "Request failed";

        return new ErrorResponse(
                status,
                error,
                message
        );
    }
}
