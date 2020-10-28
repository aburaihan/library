package com.raihan.library.service;

import com.raihan.library.model.dto.BookReturnResponseDto;

public interface BookIssueService {

  void issueBook(String isbn, String studentId, Integer issueDuration);

  BookReturnResponseDto returnBook(String isbn, String studentId);
}
