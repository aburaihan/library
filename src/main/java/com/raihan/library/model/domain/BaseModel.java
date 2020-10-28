package com.raihan.library.model.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Arrays;
import java.util.Date;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  Date createdAt;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  Date updatedAt;
  private Arrays bookIssues;

  public Arrays getBookIssues() {
    return bookIssues;
  }

  public void setBookIssues(Arrays bookIssues) {
    this.bookIssues = bookIssues;
  }
}
