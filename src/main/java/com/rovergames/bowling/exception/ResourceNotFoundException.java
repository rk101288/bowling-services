package com.rovergames.bowling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A Resource Not Found error that will throw a 404 error
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String s) {
    super(s);
  }

  public ResourceNotFoundException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public ResourceNotFoundException(Throwable throwable) {
    super(throwable);
  }
}
