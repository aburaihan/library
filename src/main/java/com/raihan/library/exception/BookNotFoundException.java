package com.raihan.library.exception;

public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(String isbn) {
    super("No book found for isbn: " + isbn);
  }
}
