package com.raihan.library.model.dto;

import lombok.Data;

@Data
public class BookCreationRequestDto {
  private String title;

  private String isbn;

  private long quantity;

  private int issueDuration;
}
