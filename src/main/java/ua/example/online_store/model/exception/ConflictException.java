package ua.example.online_store.model.exception;

public class ConflictException extends RuntimeException {

  public ConflictException(String message) {
    super(message);
  }
}
