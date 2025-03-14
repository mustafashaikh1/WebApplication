package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Testimonials;
import com.WebApplication.Repository.TestimonialsRepository;
import com.WebApplication.Service.S3Service;
import com.WebApplication.Service.TestimonialsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TestimonialsServiceImpl implements TestimonialsService {

    @Autowired
    private TestimonialsRepository testimonialsRepository;

    @Autowired
    private S3Service s3Service; // Inject S3 service

    @Override
    public Testimonials createTestimonial(Testimonials testimonial, String institutecode, MultipartFile file) throws IOException {
        testimonial.setInstitutecode(institutecode);

        // Upload image to S3 if provided
        if (file != null && !file.isEmpty()) {
            testimonial.setTestimonialImage(s3Service.uploadImage(file));
        }

        return testimonialsRepository.save(testimonial);
    }

    @Override
    public Testimonials updateTestimonial(Long id, Testimonials testimonial, MultipartFile file) throws IOException {
        Optional<Testimonials> existingTestimonialOpt = testimonialsRepository.findById(id);
        if (existingTestimonialOpt.isPresent()) {
            Testimonials existingTestimonial = existingTestimonialOpt.get();

            // Update fields except image
            existingTestimonial.setTestimonialTitle(testimonial.getTestimonialTitle());
            existingTestimonial.setTestimonialName(testimonial.getTestimonialName());
            existingTestimonial.setExam(testimonial.getExam());
            existingTestimonial.setPost(testimonial.getPost());
            existingTestimonial.setDescription(testimonial.getDescription());
            existingTestimonial.setTestimonialColor(testimonial.getTestimonialColor());

            // ✅ If a new image is uploaded, update it, otherwise retain the old image
            if (file != null && !file.isEmpty()) {
                String newImageUrl = s3Service.uploadImage(file);
                existingTestimonial.setTestimonialImage(newImageUrl);
            }

            return testimonialsRepository.save(existingTestimonial);
        } else {
            throw new RuntimeException("Testimonial not found with ID: " + id);
        }
    }


    @Override
    public void deleteTestimonialById(Long id) {
        Optional<Testimonials> testimonialOpt = testimonialsRepository.findById(id);

        if (testimonialOpt.isEmpty()) {
            throw new RuntimeException("Testimonial not found with ID: " + id);
        }

        testimonialsRepository.deleteById(id); // Delete record but keep image
        log.info("Deleted testimonial with ID: {}", id);
    }



    @Override
    public void deleteTestimonialByInstitutecode(String institutecode) {
        List<Testimonials> testimonials = testimonialsRepository.findByInstitutecode(institutecode);

        if (testimonials.isEmpty()) {
            throw new RuntimeException("No testimonials found for the given institutecode: " + institutecode);
        }

        testimonialsRepository.deleteAll(testimonials); // Delete all records
        log.info("Deleted all testimonials for institutecode: {}", institutecode);
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
