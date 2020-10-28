package com.raihan.library.model.dto;

import lombok.Data;

@Data
public class BookReturnRequestDto {

  private String isbn;

  private String studentId;

  public String getIsbn() {
    return isbn;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }
}
