package com.raihan.library.exception;

public class BookNotAvailableException extends RuntimeException {

  public BookNotAvailableException(String isbn) {
    super("Requested book is not available at the moment. ISBN: " + isbn);
  }
}
