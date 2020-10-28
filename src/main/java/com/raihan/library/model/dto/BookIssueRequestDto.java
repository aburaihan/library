package com.raihan.library.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookIssueRequestDto {

  private String isbn;

  private String studentId;

  private Integer issueDuration;

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getStudentId() {
    return studentId;
  }

  public Integer getIssueDuration() {
    return issueDuration;
  }
}
