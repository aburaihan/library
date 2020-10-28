package com.raihan.library.exception;

public class BookIssueRecordNotFoundException extends RuntimeException {

  public BookIssueRecordNotFoundException(String isbn, String studentId) {
    super("No record found for issuing of book " + isbn + " to student " + studentId);
  }
}
