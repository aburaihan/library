package com.raihan.library.repository;

import com.raihan.library.model.domain.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  Optional<Student> findByStudentId(String studentId);
}
