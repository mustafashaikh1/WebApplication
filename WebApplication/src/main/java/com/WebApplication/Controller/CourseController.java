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
@CrossOrigin(origins = "https://pjsofttech.in")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/createCourse")
    public ResponseEntity<?> createCourse(
            @RequestParam String institutecode,
            @RequestPart String courseTitle,
            @RequestPart String link,
            @RequestPart String description,
            @RequestPart String courseColor,
            @RequestPart(name = "courseImage", required = false) MultipartFile courseImage) throws IOException {

        if (institutecode == null || institutecode.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Institutecode is required and cannot be empty.");
        }

        Course course = new Course();
        course.setCourseTitle(courseTitle);
        course.setLink(link);
        course.setDescription(description);
        course.setCourseColor(courseColor);

        return ResponseEntity.ok(courseService.createCourse(course, institutecode, courseImage));
    }

    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long id,
            @RequestParam String institutecode,
            @RequestParam String courseTitle,
            @RequestParam String link,
            @RequestParam String description,
            @RequestPart String courseColor,
            @RequestPart(name = "courseImage", required = false) MultipartFile courseImage) throws IOException {

        if (institutecode == null || institutecode.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Institutecode is required and cannot be empty.");
        }

        Course course = new Course();
        course.setCourseTitle(courseTitle);
        course.setLink(link);
        course.setDescription(description);
        course.setCourseColor(courseColor);

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
    public ResponseEntity<?> getAllCourses(@RequestParam(name = "institutecode") String institutecode) {
        if (institutecode == null || institutecode.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Institutecode is required and cannot be empty.");
        }

        List<Course> courses = courseService.getAllCourses(institutecode);
        return ResponseEntity.ok(courses);
    }
}
