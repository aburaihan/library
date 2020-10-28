package com.raihan.library.service.impl;

import com.raihan.library.exception.BookNotFoundException;
import com.raihan.library.model.domain.Book;
import com.raihan.library.model.domain.Course;
import com.raihan.library.model.domain.Student;
import com.raihan.library.model.dto.BookCreationRequestDto;
import com.raihan.library.repository.BookRepository;
import com.raihan.library.service.BookService;
import com.raihan.library.service.CourseService;
import com.raihan.library.service.StudentService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BookServiceImpl implements BookService {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private CourseService courseService;

  @Autowired
  private StudentService studentService;

  @Override
  public Book createBook(BookCreationRequestDto request) {
    Book book = Book.builder()
        .title(request.getTitle())
        .isbn(request.getIsbn())
        .quantity(request.getQuantity())
        .issueDuration(request.getIssueDuration())
        .build();
    return bookRepository.save(book);
  }

  @Override
  public Book getBookByIsbn(String isbn) {
    if (StringUtils.isEmpty(isbn)) {
      throw new IllegalArgumentException("isbn cannot be empty");
    }
    return bookRepository.findByIsbn(isbn)
        .orElseThrow(() -> new BookNotFoundException(isbn));
  }

  @Override
  public Set<Book> getBooksByCourse(String courseCode) {
    Course course = courseService.getCourseByCourseCode(courseCode);
    return course.getBooks();
  }

  @Override
  public Set<Book> getBooksIssuedToStudent(String studentId) {
    Student student = studentService.getStudentByStudentId(studentId);
    return student.getBookIssues().stream()
        .filter(bkIssue -> bkIssue.getReturnedOn() == null)
        .map(bkIssue -> bkIssue.getBook())
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Student> getStudentsIssuedBook(String isbn) {
    Book book = getBookByIsbn(isbn);
    return book.getBookIssues().stream()
        .filter(bkIssue -> bkIssue.getReturnedOn() == null)
        .map(bkIssue -> bkIssue.getStudent())
        .collect(Collectors.toSet());
  }
}
