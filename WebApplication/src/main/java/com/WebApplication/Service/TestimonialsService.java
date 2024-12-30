package com.WebApplication.Service;

import com.WebApplication.Entity.Testimonials;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface TestimonialsService {

    Testimonials saveTestimonial(Testimonials testimonial, String institutecode, MultipartFile testimonialImage) throws IOException;

    boolean existsByInstitutecode(String institutecode);

    void deleteTestimonial(String institutecode);

    Optional<Testimonials> getTestimonialByInstitutecode(String institutecode);

    Optional<Testimonials> getAllTestimonials(String institutecode);

    Testimonials updateTestimonialByInstitutecode(String institutecode, Testimonials updatedTestimonial, MultipartFile file) throws IOException;
}
