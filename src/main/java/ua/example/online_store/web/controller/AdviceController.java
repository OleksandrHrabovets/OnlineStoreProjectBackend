package ua.example.online_store.web.controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;
import ua.example.online_store.model.exception.ConflictException;
import ua.example.online_store.web.dto.ApiError;

@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(AdviceController.class);
  public static final String EXCEPTION = "Exception: {}";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    log.warn("Errors of validation: {}", errors);
    return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, errors, ex));

  }

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<Object> handleNotFoundException(Exception ex) {
    log.warn(EXCEPTION, ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex));
  }

  @ExceptionHandler(ConflictException.class)
  protected ResponseEntity<Object> handleConflictException(Exception ex) {
    log.warn(EXCEPTION, ex.getMessage());
    return ResponseEntity.status(HttpStatus.valueOf(409))
        .body(new ApiError(HttpStatus.valueOf(409), ex.getMessage(), ex));
  }

  @ExceptionHandler()
  protected ResponseEntity<Object> handleAnotherException(Exception ex) {
    log.warn(EXCEPTION, ex.getMessage());
    return ResponseEntity.badRequest()
        .body(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
  }

}
