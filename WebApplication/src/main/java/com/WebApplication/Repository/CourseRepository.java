package com.WebApplication.Repository;

import com.WebApplication.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByInstitutecode(String institutecode);
    boolean existsByCourseColorAndInstitutecode(String courseColor, String institutecode);

}
