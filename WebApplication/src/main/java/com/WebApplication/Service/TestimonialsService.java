package com.WebApplication.Service;


import com.WebApplication.Entity.Testimonials;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TestimonialsService {

    Testimonials  createTestimonial(Testimonials testimonial, String institutecode, List<MultipartFile> testimonialImages) throws IOException;

    boolean existsByInstitutecode(String institutecode);

    void deleteTestimonialByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode);
    void deleteTestimonialByInstitutecode(String institutecode);
    Testimonials updateTestimonialByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode, List<MultipartFile> testimonialImages, String testimonialColor) throws IOException;

    Optional<Testimonials> getAllTestimonialsByInstitutecode(String institutecode);
}
