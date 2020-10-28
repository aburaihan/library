package com.raihan.library.service.impl;

import com.raihan.library.exception.CourseNotFoundException;
import com.raihan.library.model.domain.Course;
import com.raihan.library.repository.CourseRepository;
import com.raihan.library.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CourseServiceImpl implements CourseService {

  @Autowired
  private CourseRepository courseRepository;

  @Override
  public Course getCourseByCourseCode(String courseCode) {
    if (StringUtils.isEmpty(courseCode)) {
      throw new IllegalArgumentException("course code cannot be empty");
    }
    return courseRepository.findByCode(courseCode)
        .orElseThrow(
            () -> new CourseNotFoundException(courseCode));
  }
}
