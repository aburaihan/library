package com.raihan.library.exception;

public class CourseNotFoundException extends RuntimeException {

  public CourseNotFoundException(String courseCode) {
    super("No course found with the code: " + courseCode);
  }
}
