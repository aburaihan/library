package com.raihan.library.service.impl;

import com.raihan.library.exception.StudentNotFoundException;
import com.raihan.library.model.domain.Student;
import com.raihan.library.repository.StudentRepository;
import com.raihan.library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StudentServiceImpl implements StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Override
  public Student getStudentByStudentId(String studentId) {
    if (StringUtils.isEmpty(studentId)) {
      throw new IllegalArgumentException("Student id cannot be empty");
    }
    return studentRepository.findByStudentId(studentId)
        .orElseThrow(
            () -> new StudentNotFoundException(studentId));
  }
}
