package com.raihan.library.service;

import com.raihan.library.model.domain.Book;
import com.raihan.library.model.domain.Student;
import com.raihan.library.model.dto.BookCreationRequestDto;
import java.util.Set;

public interface BookService {

  Book createBook(BookCreationRequestDto request);

  Book getBookByIsbn(String isbn);

  Set<Book> getBooksByCourse(String courseCode);

  Set<Book> getBooksIssuedToStudent(String studentId);

  Set<Student> getStudentsIssuedBook(String isbn);
}
