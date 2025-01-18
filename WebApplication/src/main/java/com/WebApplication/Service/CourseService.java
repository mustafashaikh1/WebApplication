package com.WebApplication.Service;

import com.WebApplication.Entity.Course;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    Course createCourse(Course course, String institutecode, MultipartFile courseImage) throws IOException;
    Course updateCourse(Long id, Course course, MultipartFile courseImage) throws IOException;
    void deleteCourse(Long id);
    Course getCourseById(Long id);
    List<Course> getAllCourses(String institutecode);


    void addCourseColorByInstitutecode(String institutecode, String courseColor);

    void updateCourseColorByInstitutecode(String institutecode, String courseColor);

    boolean getColorStatusByInstitutecode(String institutecode, String courseColor);

    void deleteCourseColorByInstitutecode(String institutecode);
}
