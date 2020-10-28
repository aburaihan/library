package com.raihan.library.service.impl;

import com.raihan.library.exception.BookAlreadyIssuedException;
import com.raihan.library.exception.BookIssueRecordNotFoundException;
import com.raihan.library.exception.BookNotAvailableException;
import com.raihan.library.model.domain.Book;
import com.raihan.library.model.domain.BookIssue;
import com.raihan.library.model.domain.Student;
import com.raihan.library.model.dto.BookReturnResponseDto;
import com.raihan.library.repository.BookIssueRepository;
import com.raihan.library.service.BookIssueService;
import com.raihan.library.service.BookService;
import com.raihan.library.service.StudentService;
import com.raihan.library.util.DateTimeUtil;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookIssueServiceImpl implements BookIssueService {

  private final BookIssueRepository bookIssueRepository;

  private final BookService bookService;

  private final StudentService studentService;

  private final int finePerDay;

  @Autowired
  public BookIssueServiceImpl(
      BookIssueRepository bookIssueRepository,
      BookService bookService,
      StudentService studentService,
      @Value("${library-config.fine-per-day}") int finePerDay) {
    this.bookIssueRepository = bookIssueRepository;
    this.bookService = bookService;
    this.studentService = studentService;
    this.finePerDay = finePerDay;
  }

  @Override
  @Transactional
  public void issueBook(String isbn, String studentId, Integer issueDuration) {
    Book book = bookService.getBookByIsbn(isbn);
    if (book.getQuantity() > 0) {
      Student student = studentService.getStudentByStudentId(studentId);

      boolean alreadyIssued = student.getBookIssues()
          .stream()
          .anyMatch(
              bookIssue -> bookIssue.getBook().equals(book) && bookIssue.getReturnedOn() == null);

      if (alreadyIssued) {
        throw new BookAlreadyIssuedException(isbn, studentId);
      }

      if (issueDuration == null) {
        issueDuration = book.getIssueDuration();
      }
      book.setQuantity(book.getQuantity() - 1);
      Date issuedOn = new Date();
      BookIssue bookIssue = BookIssue.builder()
          .book(book)
          .student(student)
          .issuedOn(issuedOn)
          .expectedReturnDate(DateTimeUtil.addDays(issuedOn, issueDuration))
          .build();
      bookIssueRepository.save(bookIssue);
    } else {
      throw new BookNotAvailableException(isbn);
    }
  }

  @Override
  @Transactional
  public BookReturnResponseDto returnBook(String isbn, String studentId) {
    Book book = bookService.getBookByIsbn(isbn);
    Student student = studentService.getStudentByStudentId(studentId);

    BookIssue bookIssue;
    bookIssue = student.getBookIssues()
        .stream()
        .filter(
            bkIssue -> bkIssue.getBook().equals(book) && bkIssue.getReturnedOn() == null)
        .findFirst()
        .orElseThrow(() -> new BookIssueRecordNotFoundException(isbn, studentId));

    Date returnDate = new Date();
    long dayDifference = DateTimeUtil.dayDifference(returnDate, bookIssue.getExpectedReturnDate());
    BookReturnResponseDto bookReturnResponseDto = new BookReturnResponseDto();
    bookReturnResponseDto.setIssuedOn(bookIssue.getIssuedOn());
    bookReturnResponseDto.setReturnedOn(returnDate);
    bookReturnResponseDto.setExpectedReturnDate(bookIssue.getExpectedReturnDate());
    if (dayDifference > 0) {
      bookReturnResponseDto.setExtraDays(dayDifference);
      bookReturnResponseDto.setFine(finePerDay * dayDifference);
    }
    bookIssue.setReturnedOn(returnDate);
    book.setQuantity(book.getQuantity() + 1);
    bookIssueRepository.save(bookIssue);
    return bookReturnResponseDto;
  }
}
