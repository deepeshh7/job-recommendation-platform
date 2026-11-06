package job_recommendation_backend.dto;

import java.util.Map;

public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private Map<String, String> fieldErrors;

    public ErrorResponse(
            int status,
            String error,
            String message) {

        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(
            int status,
            String error,
            String message,
            Map<String, String> fieldErrors) {

        this.status = status;
        this.error = error;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}