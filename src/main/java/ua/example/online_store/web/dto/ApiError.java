package ua.example.online_store.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class ApiError {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;

    private Object message;

    @JsonIgnore
    private String debugMessage;

    public ApiError() {
        timeStamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, Object message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Object getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    @Override
    public String toString() {
        return "ApiError{" +
            "status=" + status +
            ", timeStamp=" + timeStamp +
            ", message='" + message + '\'' +
            ", debugMessage='" + debugMessage + '\'' +
            '}';
    }
}
