package com.raihan.library.controller;


import com.raihan.library.model.dto.BookCreationRequestDto;
import com.raihan.library.model.dto.BookIssueRequestDto;
import com.raihan.library.model.dto.BookResponseDto;
import com.raihan.library.model.dto.BookReturnRequestDto;
import com.raihan.library.model.dto.BookReturnResponseDto;
import com.raihan.library.model.dto.StudentResponseDto;
import com.raihan.library.service.BookIssueService;
import com.raihan.library.service.BookService;
import com.raihan.library.util.DtoMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@Api("Bin Controller")
public class BookController {

  @Autowired
  private BookService bookService;

  @Autowired
  private BookIssueService bookIssueService;

  @ApiOperation(value = "Get book by isbn")
  @GetMapping(value = "/isbn/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
  public BookResponseDto getBookByIsbn(@PathVariable(value = "isbn") String isbn) {
    return DtoMapperUtil.convertToBookResponse(bookService.getBookByIsbn(isbn));
  }

  @ApiOperation(value = "Create a new book")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public BookResponseDto createBook(@RequestBody BookCreationRequestDto request) {
    return DtoMapperUtil.convertToBookResponse(bookService.createBook(request));
  }

  @ApiOperation(value = "Get book by course code")
  @GetMapping(value = "/course/{course_code}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Set<BookResponseDto> getBooksByCourse(@PathVariable("course_code") String courseCode) {
    return bookService.getBooksByCourse(courseCode).stream()
        .map(book -> DtoMapperUtil.convertToBookResponse(book))
        .collect(Collectors.toSet());
  }

  @ApiOperation(value = "Get books issued to a student")
  @GetMapping(value = "/student/{student_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Set<BookResponseDto> getBooksByStudent(@PathVariable("student_id") String studentId) {
    return bookService.getBooksIssuedToStudent(studentId).stream()
        .map(book -> DtoMapperUtil.convertToBookResponse(book))
        .collect(Collectors.toSet());
  }

  @ApiOperation(value = "Get all the students that are allocated the given book")
  @GetMapping(value = "/isbn/{isbn}/students", produces = MediaType.APPLICATION_JSON_VALUE)
  public Set<StudentResponseDto> getStudents(@PathVariable("isbn") String isbn) {
    return bookService.getStudentsIssuedBook(isbn).stream()
        .map(student -> DtoMapperUtil.convertToStudentResponse(student))
        .collect(Collectors.toSet());
  }

  @ApiOperation(value = "Issue a book to a student")
  @PostMapping(value = "/issue", produces = MediaType.APPLICATION_JSON_VALUE)
  public void issueBook(@RequestBody BookIssueRequestDto request) {
    bookIssueService
        .issueBook(request.getIsbn(), request.getStudentId(), request.getIssueDuration());
  }

  @ApiOperation(value = "Return a book from a student")
  @PostMapping(value = "/return", produces = MediaType.APPLICATION_JSON_VALUE)
  public BookReturnResponseDto returnBook(@RequestBody BookReturnRequestDto request) {
    return bookIssueService
        .returnBook(request.getIsbn(), request.getStudentId());
  }

}
