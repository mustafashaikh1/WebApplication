package com.WebApplication.ServiceImpl;


import com.WebApplication.Entity.Testimonials;
import com.WebApplication.Repository.TestimonialsRepository;
import com.WebApplication.Service.S3Service;
import com.WebApplication.Service.TestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestimonialsServiceImpl implements TestimonialsService {

    @Autowired
    private TestimonialsRepository testimonialsRepository;

    @Autowired
    private S3Service s3Service;

    @Override
    public Testimonials createTestimonial(Testimonials testimonial, String institutecode, List<MultipartFile> testimonialImages) throws IOException {
        // No need to check if institutecode already exists

        if (testimonial.getTestimonialImages() == null) {
            testimonial.setTestimonialImages(new ArrayList<>());
        }
        if (testimonial.getImageUrlIds() == null) {
            testimonial.setImageUrlIds(new ArrayList<>());
        }

        int imageUrlIdCounter = 1;

        if (testimonialImages != null && !testimonialImages.isEmpty()) {
            for (MultipartFile image : testimonialImages) {
                String imageUrl = s3Service.uploadImage(image);
                testimonial.getTestimonialImages().add(imageUrl);
                testimonial.getImageUrlIds().add(imageUrlIdCounter++);
            }
        }

        return testimonialsRepository.save(testimonial);
    }


    @Override
    public Testimonials updateTestimonialById(Long testimonialId, List<MultipartFile> testimonialImages, String testimonialColor, String testimonialName, String exam, String post, String description) throws IOException {
        Optional<Testimonials> optionalTestimonial = testimonialsRepository.findById(testimonialId);

        if (optionalTestimonial.isEmpty()) {
            throw new RuntimeException("Testimonial not found with ID: " + testimonialId);
        }

        Testimonials testimonial = optionalTestimonial.get();

        // ✅ Update images
        if (testimonialImages != null && !testimonialImages.isEmpty()) {
            for (MultipartFile image : testimonialImages) {
                String newImageUrl = s3Service.uploadImage(image);
                testimonial.getTestimonialImages().add(newImageUrl);
                testimonial.getImageUrlIds().add(testimonial.getImageUrlIds().size() + 1);
            }
        }

        // ✅ Update other fields
        if (testimonialColor != null && !testimonialColor.isEmpty()) {
            testimonial.setTestimonialColor(testimonialColor);
        }
        if (testimonialName != null && !testimonialName.isEmpty()) {
            testimonial.setTestimonialName(testimonialName);
        }
        if (exam != null && !exam.isEmpty()) {
            testimonial.setExam(exam);
        }
        if (post != null && !post.isEmpty()) {
            testimonial.setPost(post);
        }
        if (description != null && !description.isEmpty()) {
            testimonial.setDescription(description);
        }

        return testimonialsRepository.save(testimonial);
    }


    @Override
    public void deleteTestimonialById(Long testimonialId) {
        Optional<Testimonials> testimonialOptional = testimonialsRepository.findById(testimonialId);

        if (testimonialOptional.isPresent()) {
            Testimonials testimonial = testimonialOptional.get();

            // Only delete testimonial data, keep images in S3
            testimonial.setTestimonialImages(null);
            testimonial.setImageUrlIds(null);
            testimonial.setTestimonialColor(null);
            testimonial.setTestimonialName(null);
            testimonial.setExam(null);
            testimonial.setPost(null);
            testimonial.setDescription(null);

            testimonialsRepository.delete(testimonial);
        } else {
            throw new RuntimeException("Testimonial not found with ID: " + testimonialId);
        }
    }



    @Override
    public void deleteTestimonialByInstitutecode(String institutecode) {
        List<Testimonials> testimonials = testimonialsRepository.findAllByInstitutecode(institutecode);

        if (!testimonials.isEmpty()) {
            testimonialsRepository.deleteAll(testimonials);
        } else {
            throw new RuntimeException("No testimonials found for the given institutecode: " + institutecode);
        }
    }

    @Override
    public Optional<Testimonials> getTestimonialById(Long testimonialId) {
        return testimonialsRepository.findById(testimonialId);
    }



    @Override
    public List<Testimonials> getAllTestimonialsByInstitutecode(String institutecode) {
        return testimonialsRepository.findAllByInstitutecode(institutecode);
    }


    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return testimonialsRepository.existsByInstitutecode(institutecode);
    }
}
