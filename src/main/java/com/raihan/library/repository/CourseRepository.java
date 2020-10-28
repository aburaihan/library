package com.raihan.library.repository;

import com.raihan.library.model.domain.Book;
import com.raihan.library.model.domain.Course;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  Optional<Course> findByCode(String courseCode);
}
