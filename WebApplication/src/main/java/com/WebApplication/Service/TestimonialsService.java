package com.WebApplication.Service;

import com.WebApplication.Entity.Testimonials;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TestimonialsService {
    Testimonials createTestimonial(Testimonials testimonial, String institutecode, MultipartFile file);
    Testimonials updateTestimonial(Long id, Testimonials testimonial, MultipartFile file);
    void deleteTestimonial(Long id);
    Testimonials getTestimonialById(Long id);
    List<Testimonials> getAllTestimonials(String institutecode);
}
