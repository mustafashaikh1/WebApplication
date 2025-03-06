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
        if (testimonialsRepository.existsByInstitutecode(institutecode)) {
            throw new RuntimeException("Testimonial with this institutecode already exists");
        }

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
    public Testimonials updateTestimonialByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode, List<MultipartFile> testimonialImages, String testimonialColor) throws IOException {
        Optional<Testimonials> optionalTestimonial = testimonialsRepository.findByImageUrlIdAndInstitutecode(imageUrlId, institutecode);

        if (optionalTestimonial.isPresent()) {
            Testimonials testimonial = optionalTestimonial.get();

            if (testimonialImages != null && !testimonialImages.isEmpty()) {
                for (MultipartFile image : testimonialImages) {
                    String newImageUrl = s3Service.uploadImage(image);
                    testimonial.getTestimonialImages().add(newImageUrl);
                    testimonial.getImageUrlIds().add(testimonial.getImageUrlIds().size() + 1);
                }
            }

            if (testimonialColor != null && !testimonialColor.isEmpty()) {
                testimonial.setTestimonialColor(testimonialColor);
            }

            return testimonialsRepository.save(testimonial);
        } else {
            throw new RuntimeException("Testimonial not found with imageUrlId: " + imageUrlId + " and institutecode: " + institutecode);
        }
    }

    @Override
    public void deleteTestimonialByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode) {
        Optional<Testimonials> testimonialOptional = testimonialsRepository.findByInstitutecode(institutecode);

        if (testimonialOptional.isPresent()) {
            Testimonials testimonial = testimonialOptional.get();

            // Ensure imageUrlIds exist before trying to remove
            if (testimonial.getImageUrlIds() == null || testimonial.getTestimonialImages() == null) {
                throw new RuntimeException("No images found for this testimonial.");
            }

            int index = testimonial.getImageUrlIds().indexOf(imageUrlId.intValue());
            if (index == -1) {
                throw new RuntimeException("Image URL ID not found in the Testimonial.");
            }

            // Remove the image ID from the database but do NOT delete the image from S3
            testimonial.getImageUrlIds().remove(index);
            testimonial.getTestimonialImages().remove(index);

            // Save updated testimonial (data only)
            testimonialsRepository.save(testimonial);

        } else {
            throw new RuntimeException("Testimonial not found for the given instituteCode.");
        }
    }


    @Override
    public void deleteTestimonialByInstitutecode(String institutecode) {
        Optional<Testimonials> testimonialOptional = testimonialsRepository.findByInstitutecode(institutecode);

        if (testimonialOptional.isPresent()) {
            Testimonials testimonial = testimonialOptional.get();

            // Keep images but delete the testimonial data
            testimonial.setTestimonialImages(null);
            testimonial.setImageUrlIds(null);
            testimonial.setTestimonialColor(null);
            testimonial.setTestimonialName(null);
            testimonial.setExam(null);
            testimonial.setPost(null);
            testimonial.setDescription(null);

            // Delete the entire testimonial record
            testimonialsRepository.delete(testimonial);
        } else {
            throw new RuntimeException("Testimonial not found for the given instituteCode.");
        }
    }


    @Override
    public Optional<Testimonials> getAllTestimonialsByInstitutecode(String institutecode) {
        return testimonialsRepository.findByInstitutecode(institutecode);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return testimonialsRepository.existsByInstitutecode(institutecode);
    }
}
