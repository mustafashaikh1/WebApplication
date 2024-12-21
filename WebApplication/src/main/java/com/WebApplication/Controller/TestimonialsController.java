package com.WebApplication.Controller;

import com.WebApplication.Entity.Testimonials;
import com.WebApplication.Service.TestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
public class TestimonialsController {

    @Autowired
    private TestimonialsService testimonialsService;

    @PostMapping("/createTestimonial")
    public ResponseEntity<Testimonials> createTestimonial(
            @RequestParam("institutecode") String institutecode, // Request param for institutecode
            @RequestParam("testimonialName") String testimonialName, // Individual fields from request part
            @RequestParam("exam") String exam,
            @RequestParam("post") String post,
            @RequestPart(value = "testimonialImage", required = false) MultipartFile testimonialImage) { // Accept MultipartFile for image

        Testimonials testimonial = new Testimonials();
        testimonial.setTestimonialName(testimonialName);
        testimonial.setExam(exam);
        testimonial.setPost(post);

        return ResponseEntity.ok(testimonialsService.createTestimonial(testimonial, institutecode, testimonialImage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Testimonials> updateTestimonial(
            @PathVariable Long id,
         // Request param for institutecode
            @RequestParam("testimonialName") String testimonialName, // Individual fields from request part
            @RequestParam("exam") String exam,
            @RequestParam("post") String post,
            @RequestPart(value = "testimonialImage", required = false) MultipartFile testimonialImage) { // Accept MultipartFile for image

        Testimonials testimonial = new Testimonials();
        testimonial.setTestimonialName(testimonialName);
        testimonial.setExam(exam);
        testimonial.setPost(post);

        return ResponseEntity.ok(testimonialsService.updateTestimonial(id, testimonial, testimonialImage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTestimonial(@PathVariable Long id) {
        testimonialsService.deleteTestimonial(id);
        return ResponseEntity.ok("Testimonial deleted successfully.");
    }


    @GetMapping("/{id}")
    public ResponseEntity<Testimonials> getTestimonialById(@PathVariable Long id) {
        return ResponseEntity.ok(testimonialsService.getTestimonialById(id));
    }


    @GetMapping("/getAllTestimonials")
    public ResponseEntity<List<Testimonials>> getAllTestimonials(@RequestParam String institutecode) {
        return ResponseEntity.ok(testimonialsService.getAllTestimonials(institutecode));
    }
}
