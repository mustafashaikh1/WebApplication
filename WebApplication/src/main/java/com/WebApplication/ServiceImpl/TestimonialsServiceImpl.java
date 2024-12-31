package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Testimonials;
import com.WebApplication.Repository.TestimonialsRepository;
import com.WebApplication.Service.TestimonialsService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TestimonialsServiceImpl implements TestimonialsService {

    @Autowired
    private TestimonialsRepository testimonialsRepository;



    @Autowired
    private Cloudinary cloudinary;

    // Helper method to upload the image to Cloudinary
    private String uploadImageToCloudinary(MultipartFile file) {
        try {
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString(); // Return the URL of the uploaded image
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        }
    }

    @Override
    public Testimonials createTestimonial(Testimonials testimonial, String institutecode, MultipartFile file) {
        testimonial.setInstitutecode(institutecode);

        // Upload image to Cloudinary
        if (file != null && !file.isEmpty()) {
            testimonial.setTestimonialImage(uploadImageToCloudinary(file));
        }

        return testimonialsRepository.save(testimonial);
    }

    @Override
    public Testimonials updateTestimonial(Long id, Testimonials testimonial, MultipartFile file) {
        Optional<Testimonials> existingTestimonialOpt = testimonialsRepository.findById(id);
        if (existingTestimonialOpt.isPresent()) {
            Testimonials existingTestimonial = existingTestimonialOpt.get();

            // Update fields
            existingTestimonial.setTestimonialName(testimonial.getTestimonialName());
            existingTestimonial.setExam(testimonial.getExam());
            existingTestimonial.setPost(testimonial.getPost());
            existingTestimonial.setDescription(testimonial.getDescription()); // Update description

            // Upload a new image if provided
            if (file != null && !file.isEmpty()) {
                existingTestimonial.setTestimonialImage(uploadImageToCloudinary(file));
            }

            return testimonialsRepository.save(existingTestimonial);
        } else {
            throw new RuntimeException("Testimonial not found with ID: " + id);
        }
    }

    @Override
    public void deleteTestimonial(Long id) {
        testimonialsRepository.deleteById(id);
    }

    @Override
    public Testimonials getTestimonialById(Long id) {
        return testimonialsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Testimonial not found with ID: " + id));
    }

    @Override
    public List<Testimonials> getAllTestimonials(String institutecode) {
        return testimonialsRepository.findByInstitutecode(institutecode);
    }
}
