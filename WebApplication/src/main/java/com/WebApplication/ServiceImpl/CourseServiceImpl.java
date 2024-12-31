package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Course;
import com.WebApplication.Repository.CourseRepository;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public Course saveCourse(Course course, String institutecode, MultipartFile courseImage) throws IOException {
        if (existsByInstitutecode(institutecode)) {
            throw new RuntimeException("A Course with institutecode '" + institutecode + "' already exists.");
        }
        course.setInstitutecode(institutecode);

        // Upload course image
        if (courseImage != null && !courseImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(courseImage);
            course.setCourseImage(imageUrl);
        }

        return courseRepository.save(course);
    }

    @Override
    public Course updateCourseByInstitutecode(String institutecode, Course updatedCourse, MultipartFile courseImage) throws IOException {
        Course existingCourse = courseRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Course not found with institutecode: " + institutecode));

        existingCourse.setCourseTitle(updatedCourse.getCourseTitle());
        existingCourse.setLink(updatedCourse.getLink());
        existingCourse.setDescription(updatedCourse.getDescription());

        // Update course image
        if (courseImage != null && !courseImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(courseImage);
            existingCourse.setCourseImage(imageUrl);
        }

        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourse(String institutecode) {
        Optional<Course> course = courseRepository.findByInstitutecode(institutecode);
        if (course.isPresent()) {
            courseRepository.delete(course.get());
        } else {
            throw new RuntimeException("Course not found with institutecode: " + institutecode);
        }
    }


    @Override
    public Optional<Course> getAllCourses(String institutecode) {
        return courseRepository.findByInstitutecode(institutecode);
    }

    @Override
    public Optional<Course> getCourseByInstitutecode(String institutecode) {
        return courseRepository.findByInstitutecode(institutecode);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return courseRepository.existsByInstitutecode(institutecode);
    }
}
