package com.raihan.library.model.domain;


import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_issues")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookIssue extends BaseModel {

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "book_id")
  private Book book;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  private Date issuedOn;

  private Date expectedReturnDate;

  private Date returnedOn;
}
