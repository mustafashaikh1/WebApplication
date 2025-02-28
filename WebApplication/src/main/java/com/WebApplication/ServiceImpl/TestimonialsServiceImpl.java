package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Testimonials;
import com.WebApplication.Repository.TestimonialsRepository;
import com.WebApplication.Service.S3Service;
import com.WebApplication.Service.TestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TestimonialsServiceImpl implements TestimonialsService {

    @Autowired
    private TestimonialsRepository testimonialsRepository;

    @Autowired
    private S3Service s3Service;  // AWS S3 Service

    @Override
    public Testimonials createTestimonial(Testimonials testimonial, String institutecode, MultipartFile file) throws IOException {
        testimonial.setInstitutecode(institutecode);

        // Upload image to S3
        if (file != null && !file.isEmpty()) {
            String imageUrl = s3Service.uploadImage(file);
            testimonial.setTestimonialImage(imageUrl);
        }

        return testimonialsRepository.save(testimonial);
    }

    @Override
    public Testimonials updateTestimonial(Long id, Testimonials testimonial, MultipartFile file) throws IOException {
        Optional<Testimonials> existingTestimonialOpt = testimonialsRepository.findById(id);

        if (existingTestimonialOpt.isPresent()) {
            Testimonials existingTestimonial = existingTestimonialOpt.get();

            // ✅ Update text fields
            existingTestimonial.setTestimonialName(testimonial.getTestimonialName());
            existingTestimonial.setExam(testimonial.getExam());
            existingTestimonial.setPost(testimonial.getPost());
            existingTestimonial.setDescription(testimonial.getDescription());
            existingTestimonial.setTestimonialColor(testimonial.getTestimonialColor());

            // ✅ If a new image is provided, add it WITHOUT deleting the previous one
            if (file != null && !file.isEmpty()) {
                // Upload new image to S3
                String newImageUrl = s3Service.uploadImage(file);

                // Append the new image to the existing image URLs (comma-separated)
                if (existingTestimonial.getTestimonialImage() != null && !existingTestimonial.getTestimonialImage().isEmpty()) {
                    existingTestimonial.setTestimonialImage(existingTestimonial.getTestimonialImage() + "," + newImageUrl);
                } else {
                    existingTestimonial.setTestimonialImage(newImageUrl);
                }
            }

            return testimonialsRepository.save(existingTestimonial);
        } else {
            throw new RuntimeException("Testimonial not found with ID: " + id);
        }
    }


    @Override
    public void deleteTestimonial(Long id) {
        Optional<Testimonials> testimonial = testimonialsRepository.findById(id);
        if (testimonial.isPresent()) {
            // Delete associated image from S3 if it exists
            if (testimonial.get().getTestimonialImage() != null) {
                s3Service.deleteImage(testimonial.get().getTestimonialImage());
            }
            testimonialsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Testimonial not found with ID: " + id);
        }
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
