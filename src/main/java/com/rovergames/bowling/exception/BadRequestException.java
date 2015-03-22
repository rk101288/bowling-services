package com.rovergames.bowling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A Bad Request call that throws a 400 error
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

  public BadRequestException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public BadRequestException(Throwable throwable) {
    super(throwable);
  }

  public BadRequestException(String s) {
    super(s);
  }
}
