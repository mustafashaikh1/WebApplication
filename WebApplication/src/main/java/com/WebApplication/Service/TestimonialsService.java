package com.WebApplication.Service;

import com.WebApplication.Entity.Testimonials;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TestimonialsService {

    // ✅ Create a new testimonial
    Testimonials createTestimonial(Testimonials testimonial, String institutecode, List<MultipartFile> testimonialImages) throws IOException;

    // ✅ Update testimonial by testimonialId
    Testimonials updateTestimonialById(Long testimonialId, List<MultipartFile> testimonialImages, String testimonialColor, String testimonialName, String exam, String post, String description) throws IOException;

    // ✅ Delete testimonial by testimonialId
    void deleteTestimonialById(Long testimonialId);

    Optional<Testimonials> getTestimonialById(Long testimonialId);

    // ✅ Delete entire testimonial by institutecode (Keep images)
    void deleteTestimonialByInstitutecode(String institutecode);

    // ✅ Get all testimonials by Institutecode
    List<Testimonials> getAllTestimonialsByInstitutecode(String institutecode);

    // ✅ Check if testimonials exist by Institutecode
    boolean existsByInstitutecode(String institutecode);
}
