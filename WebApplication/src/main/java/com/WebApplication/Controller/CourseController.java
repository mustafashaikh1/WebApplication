package com.WebApplication.Controller;

import com.WebApplication.Entity.Course;
import com.WebApplication.Service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;




@Slf4j
@RestController
//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "https://pjsofttech.in")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/createCourse")
    public ResponseEntity<Course> createCourse(
            @RequestParam String institutecode,
            @RequestPart String courseTitle,
            @RequestPart  String link,
            @RequestPart  String description,
            @RequestPart(name = "courseImage", required = false) MultipartFile courseImage) throws IOException {

        Course course = new Course();
        course.setCourseTitle(courseTitle);
        course.setLink(link);
        course.setDescription(description);

        return ResponseEntity.ok(courseService.createCourse(course, institutecode, courseImage));
    }

    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestParam String courseTitle,
            @RequestParam String link,
            @RequestParam String description,
            @RequestPart(name = "courseImage", required = false) MultipartFile courseImage) throws IOException {

        Course course = new Course();
        course.setCourseTitle(courseTitle);
        course.setLink(link);
        course.setDescription(description);

        return ResponseEntity.ok(courseService.updateCourse(id, course, courseImage));
    }

    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully.");
    }

    @GetMapping("/getCourseById/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/getAllCourses")
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(name = "institutecode") String institutecode) {
        return ResponseEntity.ok(courseService.getAllCourses(institutecode));
    }
}
