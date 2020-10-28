package com.raihan.library.util;

import com.raihan.library.model.domain.Book;
import com.raihan.library.model.domain.Student;
import com.raihan.library.model.dto.BookResponseDto;
import com.raihan.library.model.dto.StudentResponseDto;

public class DtoMapperUtil {

  public static BookResponseDto convertToBookResponse(Book book) {
    return BookResponseDto.builder()
        .isbn(book.getIsbn())
        .issueDuration(book.getIssueDuration())
        .quantity(book.getQuantity())
        .title(book.getTitle())
        .build();
  }

  public static StudentResponseDto convertToStudentResponse(Student student) {
    return StudentResponseDto.builder()
        .name(student.getName())
        .studentId(student.getStudentId())
        .build();
  }

}
