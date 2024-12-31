package com.WebApplication.Controller;

import com.WebApplication.Entity.Course;
import com.WebApplication.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/createCourse")
    public ResponseEntity<?> createCourse(@RequestParam String courseTitle,
                                          @RequestParam String institutecode,
                                          @RequestParam String link,
                                          @RequestParam String description,
                                          @RequestParam(required = false) MultipartFile courseImage) {
        try {
            if (courseService.existsByInstitutecode(institutecode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A Course with the given institutecode already exists.");
            }

            Course course = new Course();
            course.setCourseTitle(courseTitle);
            course.setInstitutecode(institutecode);
            course.setLink(link);
            course.setDescription(description);

            Course createdCourse = courseService.saveCourse(course, institutecode, courseImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload course image: " + e.getMessage());
        }
    }

    @PutMapping("/updateCourseByInstitutecode")
    public ResponseEntity<?> updateCourseByInstitutecode(@RequestParam String institutecode,
                                                         @RequestParam String courseTitle,
                                                         @RequestParam String link,
                                                         @RequestParam String description,
                                                         @RequestParam(required = false) MultipartFile courseImage) {
        try {
            Course updatedCourse = new Course();
            updatedCourse.setCourseTitle(courseTitle);
            updatedCourse.setLink(link);
            updatedCourse.setDescription(description);

            Course result = courseService.updateCourseByInstitutecode(institutecode, updatedCourse, courseImage);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload course image: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteCourseByInstitutecode")
    public ResponseEntity<?> deleteCourseByInstitutecode(@RequestParam String institutecode) {
        try {
            courseService.deleteCourse(institutecode);
            return ResponseEntity.ok("Course deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getCourseByInstitutecode")
    public ResponseEntity<Course> getCourseByInstitutecode(@RequestParam String institutecode) {
        return courseService.getCourseByInstitutecode(institutecode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/getAllCourses")
    public ResponseEntity<Optional<Course>> getAllCourses(@RequestParam String institutecode) {
        return ResponseEntity.ok(courseService.getAllCourses(institutecode));
    }
}
