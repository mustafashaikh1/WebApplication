package com.WebApplication.Controller;

import com.WebApplication.Entity.Course;
import com.WebApplication.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/createCourse")
    public ResponseEntity<Course> createCourse(
            @RequestParam String institutecode,
            @RequestParam String courseTitle,
            @RequestParam String link,
            @RequestParam String description,
            @RequestPart(required = false) MultipartFile courseImage) throws IOException {

        Course course = new Course();
        course.setCourseTitle(courseTitle);
        course.setLink(link);
        course.setDescription(description);

        // Create course and apply color if already exists for the institutecode
        Course createdCourse = courseService.createCourse(course, institutecode, courseImage);

        return ResponseEntity.ok(createdCourse);
    }
    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestParam String courseTitle,
            @RequestParam String link,
            @RequestParam String description,
//            @RequestParam String courseColor,
            @RequestPart(required = false) MultipartFile courseImage) throws IOException {

        Course course = new Course();
        course.setCourseTitle(courseTitle);
        course.setLink(link);
        course.setDescription(description);
//        course.setCourseColor(courseColor);

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
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam String institutecode) {
        return ResponseEntity.ok(courseService.getAllCourses(institutecode));
    }

    // New endpoint for adding courseColor to all courses of the same institutecode
    @PostMapping("/addCourseColor")
    public ResponseEntity<String> addCourseColor(
            @RequestParam String institutecode,
            @RequestParam String courseColor) {

        // Add or update color for the given institutecode
        courseService.addCourseColorByInstitutecode(institutecode, courseColor);

        return ResponseEntity.ok("Course color created successfully for institutecode " + institutecode);
    }


    @PutMapping("/updateCourseColor")  // Use PUT instead of POST for updating
    public ResponseEntity<String> updateCourseColor(
            @RequestParam String institutecode,
            @RequestParam String courseColor) {

        courseService.updateCourseColorByInstitutecode(institutecode, courseColor);
        return ResponseEntity.ok("Course color updated successfully for institutecode: " + institutecode);
    }

    @DeleteMapping("/deleteCourseColor")
    public ResponseEntity<String> deleteCourseColor(
            @RequestParam String institutecode) {

        courseService.deleteCourseColorByInstitutecode(institutecode);
        return ResponseEntity.ok("Course color deleted successfully for institutecode: " + institutecode);
    }
}
