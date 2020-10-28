package com.raihan.library.repository;

import com.raihan.library.model.domain.Book;
import com.raihan.library.model.domain.BookIssue;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {


}
