package com.raihan.library.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookReturnResponseDto {
  private Date issuedOn;

  private Date returnedOn;

  private Date expectedReturnDate;

  private long extraDays;

  private long fine;
}
