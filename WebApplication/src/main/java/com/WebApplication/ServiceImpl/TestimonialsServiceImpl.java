package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.ContactForm;
import com.WebApplication.Entity.Facility;
import com.WebApplication.Entity.Testimonials;
import com.WebApplication.Repository.TestimonialsRepository;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.TestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class TestimonialsServiceImpl implements TestimonialsService {

    @Autowired
    private TestimonialsRepository testimonialsRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public Testimonials saveTestimonial(Testimonials testimonial, String institutecode, MultipartFile testimonialImage) throws IOException {
        if (existsByInstitutecode(institutecode)) {
            throw new RuntimeException("A Testimonial with institutecode '" + institutecode + "' already exists.");
        }
        testimonial.setInstitutecode(institutecode);

        // Upload testimonial image
        if (testimonialImage != null && !testimonialImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(testimonialImage);
            testimonial.setTestimonialImage(imageUrl);
        }

        return testimonialsRepository.save(testimonial);
    }

    @Override
    public Testimonials updateTestimonialByInstitutecode(String institutecode, Testimonials updatedTestimonial, MultipartFile file) throws IOException {
        Testimonials existingTestimonial = testimonialsRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Testimonial not found with ID: " + institutecode));

        existingTestimonial.setTestimonialName(updatedTestimonial.getTestimonialName());
        existingTestimonial.setExam(updatedTestimonial.getExam());
        existingTestimonial.setPost(updatedTestimonial.getPost());
        existingTestimonial.setDescription(updatedTestimonial.getDescription());

        // Update testimonial image
        if (file != null && !file.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(file);
            existingTestimonial.setTestimonialImage(imageUrl);
        }

        return testimonialsRepository.save(existingTestimonial);
    }





    @Override
    public void deleteTestimonial(String institutecode) {
        // Attempt to find the ContactForm by institutecode
        Testimonials testimonials = testimonialsRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("No record found with the given institutecode: " + institutecode));

        // Delete the found ContactForm
        testimonialsRepository.delete(testimonials);
    }

    @Override
    public Optional<Testimonials> getTestimonialByInstitutecode(String institutecode) {
        return testimonialsRepository.findByInstitutecode(institutecode).stream().findFirst();
    }


    @Override
    public Optional<Testimonials> getAllTestimonials(String institutecode) {
        return testimonialsRepository.findByInstitutecode(institutecode);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return !testimonialsRepository.findByInstitutecode(institutecode).isEmpty();
    }
}
