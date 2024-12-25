package com.WebApplication.Controller;

import com.WebApplication.Entity.Testimonials;
import com.WebApplication.Service.TestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "https://pjsofttech.in")
public class TestimonialsController {

    @Autowired
    private TestimonialsService testimonialsService;

    @PostMapping("/createTestimonial")
    public ResponseEntity<Testimonials> createTestimonial(
            @RequestParam String institutecode, // Request param for institutecode
            @RequestParam String testimonialName, // Individual fields from request part
            @RequestParam String exam,
            @RequestParam  String post,
            @RequestPart(value = "testimonialImage", required = false) MultipartFile testimonialImage) { // Accept MultipartFile for image

        Testimonials testimonial = new Testimonials();
        testimonial.setTestimonialName(testimonialName);
        testimonial.setExam(exam);
        testimonial.setPost(post);

        return ResponseEntity.ok(testimonialsService.createTestimonial(testimonial, institutecode, testimonialImage));
    }

    @PutMapping("/updateTestimonial/{id}")
    public ResponseEntity<Testimonials> updateTestimonial(
            @PathVariable Long id,
         // Request param for institutecode
            @RequestParam  String testimonialName, // Individual fields from request part
            @RequestParam String exam,
            @RequestParam  String post,
            @RequestPart(value = "testimonialImage", required = false) MultipartFile testimonialImage) { // Accept MultipartFile for image

        Testimonials testimonial = new Testimonials();
        testimonial.setTestimonialName(testimonialName);
        testimonial.setExam(exam);
        testimonial.setPost(post);

        return ResponseEntity.ok(testimonialsService.updateTestimonial(id, testimonial, testimonialImage));
    }

    @DeleteMapping("/deleteTestimonial/{id}")
    public ResponseEntity<String> deleteTestimonial(@PathVariable Long id) {
        testimonialsService.deleteTestimonial(id);
        return ResponseEntity.ok("Testimonial deleted successfully.");
    }


    @GetMapping("/getTestimonialById/{id}")
    public ResponseEntity<Testimonials> getTestimonialById(@PathVariable Long id) {
        return ResponseEntity.ok(testimonialsService.getTestimonialById(id));
    }


    @GetMapping("/getAllTestimonials")
    public ResponseEntity<List<Testimonials>> getAllTestimonials(@RequestParam String institutecode) {
        return ResponseEntity.ok(testimonialsService.getAllTestimonials(institutecode));
    }
}
