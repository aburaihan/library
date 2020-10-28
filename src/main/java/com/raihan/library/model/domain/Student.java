package com.raihan.library.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Student extends BaseModel {

  private String name;

  private String studentId;

  @JsonIgnore
  @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "student", fetch = FetchType.LAZY)
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
    Student student = (Student) o;
    return Objects.equals(name, student.name) &&
        Objects.equals(studentId, student.studentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, studentId);
  }
}
