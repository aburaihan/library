package com.raihan.library.service;

import com.raihan.library.model.domain.Course;

public interface CourseService {

  Course getCourseByCourseCode(String courseCode);
}
