package com.raihan.library.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDto {
  private String title;

  private long quantity;

  private String isbn;

  private int issueDuration;

}
