package com.WebApplication.Repository;

import com.WebApplication.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.institutecode = :institutecode")
    Optional<Course> findByInstitutecode(@Param("institutecode") String institutecode);

    @Query("SELECT c FROM Course c WHERE c.institutecode = :institutecode")
    List<Course> findAllByInstitutecode(@Param("institutecode") String institutecode);

    boolean existsByInstitutecode(String institutecode);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.institutecode = :institutecode")
    long countByInstitutecode(@Param("institutecode") String institutecode);

    @Query("SELECT c FROM Course c WHERE LOWER(c.courseTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) AND c.institutecode = :institutecode")
    List<Course> searchCoursesByTitleAndInstitutecode(@Param("keyword") String keyword, @Param("institutecode") String institutecode);
}
