package com.WebApplication.Repository;

import com.WebApplication.Entity.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByInstitutecode(String institutecode);


    // Custom query to update courseColor for all courses with a specific institutecode
    @Modifying
    @Transactional
    @Query("UPDATE Course c SET c.courseColor = :courseColor WHERE c.institutecode = :institutecode")
    void addCourseColorByInstitutecode(String institutecode, String courseColor);


    @Modifying
    @Transactional
    @Query("UPDATE Course c SET c.courseColor = :courseColor WHERE c.institutecode = :institutecode")
    void updateCourseColorByInstitutecode(String institutecode, String courseColor);

    List<Course> findByInstitutecodeAndCourseColor(String institutecode, String courseColor);
}
