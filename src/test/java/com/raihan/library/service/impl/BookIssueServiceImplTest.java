package com.raihan.library.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.raihan.library.exception.BookAlreadyIssuedException;
import com.raihan.library.exception.BookIssueRecordNotFoundException;
import com.raihan.library.exception.BookNotAvailableException;
import com.raihan.library.model.domain.Book;
import com.raihan.library.model.domain.BookIssue;
import com.raihan.library.model.domain.Student;
import com.raihan.library.model.dto.BookReturnResponseDto;
import com.raihan.library.repository.BookIssueRepository;
import com.raihan.library.service.BookService;
import com.raihan.library.service.StudentService;
import com.raihan.library.util.DateTimeUtil;
import java.util.Date;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BookIssueServiceImplTest {

  private BookIssueServiceImpl bookIssueService;

  @Mock
  private BookIssueRepository bookIssueRepository;

  @Mock
  private BookService bookService;

  @Mock
  private StudentService studentService;

  @Before
  public void setup() {
    bookIssueService = new BookIssueServiceImpl(bookIssueRepository, bookService, studentService,
        20);
  }

  @Test(expected = BookAlreadyIssuedException.class)
  public void issueBookShouldThrowExceptionIfBookAlreadyIssued() {
    Book book = getBook();
    Student student = getStudent();
    student.setBookIssues(new HashSet<>());
    student.getBookIssues().add(getBookIssue(student, book));

    when(bookService.getBookByIsbn(any())).thenReturn(book);
    when(studentService.getStudentByStudentId(any())).thenReturn(student);

    bookIssueService.issueBook("1234567", "UNI1234", 3);
  }

  @Test(expected = BookNotAvailableException.class)
  public void issueBookShouldThrowExceptionIfBookQuantityIsZero() {
    Book book = getBook();
    book.setQuantity(0);
    Student student = getStudent();

    when(bookService.getBookByIsbn(any())).thenReturn(book);
    when(studentService.getStudentByStudentId(any())).thenReturn(student);

    bookIssueService.issueBook("1234567", "UNI1234", 3);
  }

  @Test
  public void shouldIssueBook() {
    Book book = getBook();
    Student student = getStudent();

    when(bookService.getBookByIsbn(any())).thenReturn(book);
    when(studentService.getStudentByStudentId(any())).thenReturn(student);

    bookIssueService.issueBook("1234567", "UNI1234", 3);

    ArgumentCaptor<BookIssue> argumentCaptor = ArgumentCaptor.forClass(BookIssue.class);
    verify(bookIssueRepository).save(argumentCaptor.capture());

    BookIssue bookIssue = argumentCaptor.getValue();
    assertEquals(book, bookIssue.getBook());
    assertEquals(student, bookIssue.getStudent());
    assertEquals(9, book.getQuantity());
  }

  @Test
  public void returnBookShouldReturnTheBook() {
    Book book = getBook();
    Student student = getStudent();

    student.setBookIssues(new HashSet<>());
    BookIssue bookIssue = getBookIssue(student, book);
    bookIssue.setExpectedReturnDate(new Date());
    student.getBookIssues().add(bookIssue);

    assertNull(bookIssue.getReturnedOn());

    when(bookService.getBookByIsbn(any())).thenReturn(book);
    when(studentService.getStudentByStudentId(any())).thenReturn(student);

    BookReturnResponseDto response = bookIssueService.returnBook("1234567", "UNI1234");

    assertEquals(11, book.getQuantity());
    assertNotNull(bookIssue.getReturnedOn());

    assertEquals(0, response.getFine());
    assertEquals(0, response.getExtraDays());
  }

  @Test
  public void returnBookShouldReturnTheBookWithFine() {
    Book book = getBook();
    Student student = getStudent();

    student.setBookIssues(new HashSet<>());
    BookIssue bookIssue = getBookIssue(student, book);
    bookIssue.setExpectedReturnDate(DateTimeUtil.addDays(new Date(), -5));
    student.getBookIssues().add(bookIssue);

    assertNull(bookIssue.getReturnedOn());

    when(bookService.getBookByIsbn(any())).thenReturn(book);
    when(studentService.getStudentByStudentId(any())).thenReturn(student);

    BookReturnResponseDto response = bookIssueService.returnBook("1234567", "UNI1234");

    assertEquals(11, book.getQuantity());
    assertNotNull(bookIssue.getReturnedOn());

    assertEquals(100, response.getFine());
    assertEquals(5, response.getExtraDays());
  }


  @Test(expected = BookIssueRecordNotFoundException.class)
  public void returnBookShouldThrowExceptionIfNoBookIssued() {
    Book book = getBook();
    Student student = getStudent();

    when(bookService.getBookByIsbn(any())).thenReturn(book);
    when(studentService.getStudentByStudentId(any())).thenReturn(student);

    bookIssueService.returnBook("1234567", "UNI1234");
  }


  private Student getStudent() {
    Student student = new Student();
    student.setName("Mock Mock");
    student.setStudentId("UNI1234");
    return student;
  }

  private Book getBook() {
    return Book.builder()
        .quantity(10)
        .isbn("1234567")
        .title("MOCK BOOK")
        .build();
  }

  private BookIssue getBookIssue(Student student, Book book) {
    return BookIssue.builder().student(student).book(book).build();
  }

}