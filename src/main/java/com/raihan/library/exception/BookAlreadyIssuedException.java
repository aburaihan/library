package com.raihan.library.exception;

public class BookAlreadyIssuedException extends RuntimeException {

  public BookAlreadyIssuedException(String isbn, String studentId) {
    super("Book with isbn " + isbn + "is already issued to student id " + studentId);
  }
}
