package com.WebApplication.Controller;

import com.WebApplication.Entity.Facility;
import com.WebApplication.Entity.Testimonials;
import com.WebApplication.Service.TestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class TestimonialsController {

    @Autowired
    private TestimonialsService testimonialsService;

    @PostMapping("/createTestimonial")
    public ResponseEntity<?> createTestimonial(@RequestParam String testimonialName,
                                               @RequestParam String exam,
                                               @RequestParam String post,
                                               @RequestParam String description,
                                               @RequestParam String institutecode,
                                               @RequestParam(required = false) MultipartFile testimonialImage) {
        try {
            if (testimonialsService.existsByInstitutecode(institutecode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A Testimonial with the given institutecode already exists.");
            }

            Testimonials testimonial = new Testimonials();
            testimonial.setTestimonialName(testimonialName);
            testimonial.setExam(exam);
            testimonial.setPost(post);
            testimonial.setDescription(description);
            testimonial.setInstitutecode(institutecode);

            Testimonials createdTestimonial = testimonialsService.saveTestimonial(testimonial, institutecode, testimonialImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTestimonial);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload testimonial image: " + e.getMessage());
        }
    }

    @PutMapping("/updateTestimonialByInstitutecode")
    public ResponseEntity<?> updateTestimonialByInstitutecode(@RequestParam String institutecode,
                                                              @RequestParam String testimonialName,
                                                              @RequestParam String exam,
                                                              @RequestParam String post,
                                                              @RequestParam String description,
                                                              @RequestParam(required = false) MultipartFile file) {
        try {
            Testimonials updatedTestimonial = new Testimonials();
            updatedTestimonial.setTestimonialName(testimonialName);
            updatedTestimonial.setExam(exam);
            updatedTestimonial.setPost(post);
            updatedTestimonial.setDescription(description);

            Testimonials result = testimonialsService.updateTestimonialByInstitutecode(institutecode, updatedTestimonial, file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload testimonial image: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @DeleteMapping("/deleteTestimonial")
    public ResponseEntity<String> deleteTestimonial(@RequestParam String institutecode) {
        try {
            testimonialsService.deleteTestimonial(institutecode);
            return ResponseEntity.ok("Testimonial deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete Testimonial: " + e.getMessage());
        }
    }



    @GetMapping("/getTestimonialByInstitutecode")
    public ResponseEntity<Testimonials> getTestimonialByInstitutecode(@RequestParam String institutecode) {
        return testimonialsService.getTestimonialByInstitutecode(institutecode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/getAllTestimonials")
    public ResponseEntity<Optional<Testimonials>> getAllTestimonials(@RequestParam String institutecode) {
        return ResponseEntity.ok(testimonialsService.getAllTestimonials(institutecode));
    }



}
