package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Course;
import com.WebApplication.Repository.CourseRepository;
import com.WebApplication.Service.CourseService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Course createCourse(Course course, String institutecode, MultipartFile courseImage) throws IOException {
        course.setInstitutecode(institutecode);
        if (courseImage != null && !courseImage.isEmpty()) {
            Map<String, String> uploadResult = cloudinary.uploader().upload(courseImage.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = uploadResult.get("secure_url");
            course.setCourseImage(imageUrl);
        }
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course, MultipartFile courseImage) throws IOException {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            Course updatedCourse = existingCourse.get();
            updatedCourse.setCourseTitle(course.getCourseTitle());
            updatedCourse.setLink(course.getLink());
            updatedCourse.setDescription(course.getDescription());
//            updatedCourse.setCourseColor(course.getCourseColor());

            if (courseImage != null && !courseImage.isEmpty()) {
                Map<String, String> uploadResult = cloudinary.uploader().upload(courseImage.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = uploadResult.get("secure_url");
                updatedCourse.setCourseImage(imageUrl);
            }

            return courseRepository.save(updatedCourse);
        } else {
            throw new RuntimeException("Course not found with id: " + id);
        }
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
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
}
