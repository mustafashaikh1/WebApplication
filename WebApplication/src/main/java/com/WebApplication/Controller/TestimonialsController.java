package com.WebApplication.Controller;


import com.WebApplication.Entity.Testimonials;
import com.WebApplication.Service.TestimonialsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = {
        "https://pjsofttech.in",
        "https://live.ooacademy.co.in",
        "https://course.yashodapublication.com"
})
public class TestimonialsController {

    @Autowired
    private TestimonialsService testimonialsService;

    // ✅ Create Testimonial
    @PostMapping("/createTestimonial")
    public ResponseEntity<?> createTestimonial(@RequestParam String testimonialName,
                                               @RequestParam String exam,
                                               @RequestParam String post,
                                               @RequestParam String description,
                                               @RequestParam String testimonialColor,
                                               @RequestParam String institutecode,
                                               @RequestParam(required = false) List<MultipartFile> testimonialImages) {
        try {
            Testimonials testimonial = new Testimonials();
            testimonial.setTestimonialName(testimonialName);
            testimonial.setExam(exam);
            testimonial.setPost(post);
            testimonial.setDescription(description);
            testimonial.setTestimonialColor(testimonialColor);
            testimonial.setInstitutecode(institutecode);

            Testimonials createdTestimonial = testimonialsService.createTestimonial(testimonial, institutecode, testimonialImages);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTestimonial);
        } catch (IOException e) {
            log.error("Failed to create Testimonial: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create Testimonial: " + e.getMessage());
        }
    }

    // ✅ Update Testimonial by ImageUrlId and Institutecode (Without Deleting Previous Images)
    @PutMapping("/updateTestimonialByImageUrlIdAndInstitutecode")
    public ResponseEntity<?> updateTestimonialByImageUrlIdAndInstitutecode(
            @RequestParam Long imageUrlId,
            @RequestParam String institutecode,
            @RequestParam(required = false) List<MultipartFile> testimonialImages,
            @RequestParam(required = false) String testimonialColor) {
        try {
            Testimonials updatedTestimonial = testimonialsService.updateTestimonialByImageUrlIdAndInstitutecode(imageUrlId, institutecode, testimonialImages, testimonialColor);
            return ResponseEntity.ok(updatedTestimonial);
        } catch (RuntimeException e) {
            log.error("Testimonial update failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Testimonial update failed: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error uploading images: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading images: " + e.getMessage());
        }
    }


    // ✅ Delete only Testimonial data by ImageUrlId and Institutecode (Keep Image in S3)
    @DeleteMapping("/deleteTestimonial")
    public ResponseEntity<String> deleteTestimonialByImageUrlIdAndInstitutecode(
            @RequestParam Long imageUrlId,
            @RequestParam String institutecode) {
        try {
            testimonialsService.deleteTestimonialByImageUrlIdAndInstitutecode(imageUrlId, institutecode);
            return ResponseEntity.ok("Image URL ID removed from database, but image remains in S3.");
        } catch (RuntimeException e) {
            log.error("Testimonial deletion failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Testimonial deletion failed: " + e.getMessage());
        }
    }

    // ✅ Delete entire Testimonial data but keep images
    @DeleteMapping("/deleteTestimonialByInstitutecode")
    public ResponseEntity<String> deleteTestimonialByInstitutecode(
            @RequestParam String institutecode) {
        try {
            testimonialsService.deleteTestimonialByInstitutecode(institutecode);
            return ResponseEntity.ok("Testimonial data deleted, but images remain in S3.");
        } catch (RuntimeException e) {
            log.error("Testimonial deletion failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Testimonial deletion failed: " + e.getMessage());
        }
    }
    // ✅ Get all Testimonials by Institutecode
    @GetMapping("/getAllTestimonials")
    public ResponseEntity<Optional<Testimonials>> getAllTestimonialsByInstitutecode(@RequestParam String institutecode) {
        Optional<Testimonials> testimonials = testimonialsService.getAllTestimonialsByInstitutecode(institutecode);
        return ResponseEntity.ok(testimonials);
    }
}
