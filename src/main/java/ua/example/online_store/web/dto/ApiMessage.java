package ua.example.online_store.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class ApiMessage {

  private HttpStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timeStamp;

  private Object message;

  public ApiMessage() {
    timeStamp = LocalDateTime.now();
  }

  public ApiMessage(HttpStatus status) {
    this();
    this.status = status;
  }

  public ApiMessage(HttpStatus status, Object message) {
    this();
    this.status = status;
    this.message = message;
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

  @Override
  public String toString() {
    return "ApiError{" +
        "status=" + status +
        ", timeStamp=" + timeStamp +
        ", message='" + message + '\'' +
        '}';
  }
}
