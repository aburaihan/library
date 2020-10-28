package com.raihan.library.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "courses")
@Data
public class Course extends BaseModel {

  private String name;

  private String code;

  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
      name = "course_book_mappings",
      joinColumns = {@JoinColumn(name = "course_id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id")}
  )
  Set<Book> books = new HashSet<>();

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
    Course course = (Course) o;
    return Objects.equals(name, course.name) &&
        Objects.equals(code, course.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, code);
  }
}
