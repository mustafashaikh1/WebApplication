package com.WebApplication.Service;

import com.WebApplication.Entity.Course;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface CourseService {

    Course saveCourse(Course course, String institutecode, MultipartFile courseImage) throws IOException;

    boolean existsByInstitutecode(String institutecode);

    void deleteCourse(String institutecode);

    Optional<Course> getCourseByInstitutecode(String institutecode);

    Optional<Course> getAllCourses(String institutecode);

    Course updateCourseByInstitutecode(String institutecode, Course updatedCourse, MultipartFile courseImage) throws IOException;
}
