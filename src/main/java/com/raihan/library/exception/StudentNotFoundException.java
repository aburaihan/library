package com.raihan.library.exception;

public class StudentNotFoundException extends RuntimeException {

  public StudentNotFoundException(String studentId) {
    super("Student not found with student id: " + studentId);
  }
}
