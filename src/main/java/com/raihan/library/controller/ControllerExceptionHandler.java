package com.raihan.library.controller;

import com.raihan.library.exception.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"com.raihan.library"})
public class ControllerExceptionHandler {

  @ExceptionHandler(BookAlreadyIssuedException.class)
  public ResponseEntity<ErrorResponse> handleConflictException(RuntimeException e) {
    return getErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler({
      BookIssueRecordNotFoundException.class,
      BookNotFoundException.class,
      CourseNotFoundException.class,
      StudentNotFoundException.class
  })
  public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e) {
    return getErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BookNotAvailableException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(RuntimeException e) {
    return getErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    return getErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponse> getErrorResponse(String message,
      HttpStatus httpStatus) {
    return new ResponseEntity<>(new ErrorResponse(message, httpStatus), httpStatus);
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class ErrorResponse {

    private String message;
    private HttpStatus status;

    public ErrorResponse(String message, HttpStatus httpStatus) {
    }
  }
}
