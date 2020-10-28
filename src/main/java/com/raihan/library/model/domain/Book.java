package com.raihan.library.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Book extends BaseModel {

  private String title;

  private long quantity;

  private String isbn;

  private int issueDuration;

  @JsonIgnore
  @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
  private Set<Course> courses = new HashSet<>();

  @JsonIgnore
  @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "book", fetch = FetchType.LAZY)
  private Set<BookIssue> bookIssues = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Book book = (Book) o;
    return quantity == book.quantity &&
        issueDuration == book.issueDuration &&
        Objects.equals(title, book.title) &&
        Objects.equals(isbn, book.isbn);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(super.hashCode(), title, quantity, isbn, issueDuration);
  }
}
