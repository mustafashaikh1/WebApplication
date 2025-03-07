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
import java.util.Collections;
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
    @PutMapping("/updateTestimonialById")
    public ResponseEntity<?> updateTestimonialById(
            @RequestParam Long testimonialId,
            @RequestParam(required = false) List<MultipartFile> testimonialImages,
            @RequestParam(required = false) String testimonialColor,
            @RequestParam(required = false) String testimonialName,
            @RequestParam(required = false) String exam,
            @RequestParam(required = false) String post,
            @RequestParam(required = false) String description) {
        try {
            Testimonials updatedTestimonial = testimonialsService.updateTestimonialById(
                    testimonialId, testimonialImages, testimonialColor, testimonialName, exam, post, description);
            return ResponseEntity.ok(updatedTestimonial);
        } catch (RuntimeException e) {
            log.error("Testimonial update failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Testimonial update failed: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error uploading images: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading images: " + e.getMessage());
        }
    }



    // ✅ Delete Testimonial by ID
    @DeleteMapping("/deleteTestimonialById")
    public ResponseEntity<String> deleteTestimonialById(@RequestParam Long testimonialId) {
        try {
            testimonialsService.deleteTestimonialById(testimonialId);
            return ResponseEntity.ok("Testimonial deleted successfully, but images remain in S3.");
        } catch (RuntimeException e) {
            log.error("Testimonial deletion failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Testimonial deletion failed: " + e.getMessage());
        }
    }

    // ✅ Delete all Testimonials by Institutecode
    @DeleteMapping("/deleteTestimonialsByInstitutecode")
    public ResponseEntity<String> deleteTestimonialsByInstitutecode(@RequestParam String institutecode) {
        try {
            testimonialsService.deleteTestimonialByInstitutecode(institutecode);
            return ResponseEntity.ok("All testimonials for the institute deleted, but images remain in S3.");
        } catch (RuntimeException e) {
            log.error("Testimonial deletion failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Testimonial deletion failed: " + e.getMessage());
        }
    }

    // ✅ Get Testimonial by ID
    @GetMapping("/getTestimonialById")
    public ResponseEntity<?> getTestimonialById(@RequestParam Long testimonialId) {
        Optional<Testimonials> testimonial = testimonialsService.getTestimonialById(testimonialId);

        if (testimonial.isPresent()) {
            return ResponseEntity.ok(testimonial.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Testimonial not found with ID: " + testimonialId);
        }
    }


    @GetMapping("/getAllTestimonials")
    public ResponseEntity<List<Testimonials>> getAllTestimonialsByInstitutecode(@RequestParam String institutecode) {
        List<Testimonials> testimonials = testimonialsService.getAllTestimonialsByInstitutecode(institutecode);

        if (testimonials.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        return ResponseEntity.ok(testimonials);
    }

}
