package uz.pdp.springsecuritypcmarket.payload.response;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String errors) {
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(errors);
    }
}
