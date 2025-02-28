package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Course;
import com.WebApplication.Repository.CourseRepository;
import com.WebApplication.Service.CourseService;
import com.WebApplication.Service.S3Service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private S3Service s3Service;  // AWS S3 Service

    @Override
    public Course createCourse(Course course, String institutecode, MultipartFile courseImage) throws IOException {
        course.setInstitutecode(institutecode);

        // Fetch the existing course color for the institutecode (if any)
        List<String> existingCourseColors = courseRepository.findCourseColorByInstitutecode(institutecode);

        // If colors exist, set the first one (or handle it based on your business logic)
        if (!existingCourseColors.isEmpty()) {
            course.setCourseColor(existingCourseColors.get(0)); // Set the first color found
        }

        // Handle course image upload (if any)
        if (courseImage != null && !courseImage.isEmpty()) {
            String imageUrl = s3Service.uploadImage(courseImage);  // Upload image to S3
            course.setCourseImage(imageUrl);
        }

        // Save the course
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course, MultipartFile courseImage) throws IOException {
        Optional<Course> existingCourseOpt = courseRepository.findById(id);

        if (existingCourseOpt.isPresent()) {
            Course existingCourse = existingCourseOpt.get();

            // Update text fields
            existingCourse.setCourseTitle(course.getCourseTitle());
            existingCourse.setLink(course.getLink());
            existingCourse.setDescription(course.getDescription());

            // If a new image is provided, update it; otherwise, retain the old one
            if (courseImage != null && !courseImage.isEmpty()) {
                String imageUrl = s3Service.uploadImage(courseImage);
                existingCourse.setCourseImage(imageUrl);
            }

            return courseRepository.save(existingCourse);
        } else {
            throw new RuntimeException("Course not found with ID: " + id);
        }
    }


    @Override
    public void deleteCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            // Delete associated image from S3 if exists
            if (course.get().getCourseImage() != null) {
                s3Service.deleteImage(course.get().getCourseImage());
            }
            courseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Course not found with ID: " + id);
        }
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));
    }

    @Override
    public List<Course> getAllCourses(String institutecode) {
        return courseRepository.findByInstitutecode(institutecode);
    }

    @Override
    public void addCourseColorByInstitutecode(String institutecode, String courseColor) {
        courseRepository.addCourseColorByInstitutecode(institutecode, courseColor);
    }

    @Override
    public void updateCourseColorByInstitutecode(String institutecode, String courseColor) {
        courseRepository.updateCourseColorByInstitutecode(institutecode, courseColor);
    }

    @Override
    public boolean getColorStatusByInstitutecode(String institutecode, String courseColor) {
        // Query the database to find courses by institutecode and courseColor
        List<Course> courses = courseRepository.findByInstitutecodeAndCourseColor(institutecode, courseColor);
        return !courses.isEmpty(); // Return true if courses are found, false otherwise
    }

    @Override
    public void deleteCourseColorByInstitutecode(String institutecode) {
        courseRepository.deleteCourseColorByInstitutecode(institutecode);
    }
}
