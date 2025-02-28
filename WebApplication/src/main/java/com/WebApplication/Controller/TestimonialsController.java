package com.WebApplication.Controller;

import com.WebApplication.Entity.Testimonials;
import com.WebApplication.Service.TestimonialsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


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

    @PostMapping("/createTestimonial")
    public ResponseEntity<Testimonials> createTestimonial(
            @RequestParam String institutecode,
            @RequestParam String testimonialName,
            @RequestParam String exam,
            @RequestParam String post,
            @RequestParam String description, // New field
            @RequestParam String testimonialColor,
            @RequestPart(value = "testimonialImage", required = false) MultipartFile testimonialImage) throws IOException {

        Testimonials testimonial = new Testimonials();
        testimonial.setTestimonialName(testimonialName);
        testimonial.setExam(exam);
        testimonial.setPost(post);
        testimonial.setDescription(description); // Set description
        testimonial.setTestimonialColor(testimonialColor);

        return ResponseEntity.ok(testimonialsService.createTestimonial(testimonial, institutecode, testimonialImage));
    }

    @PutMapping("/updateTestimonial/{id}")
    public ResponseEntity<Testimonials> updateTestimonial(
            @PathVariable Long id,
            @RequestParam String testimonialName,
            @RequestParam String exam,
            @RequestParam String post,
            @RequestParam String description,
            @RequestParam String testimonialColor,
            @RequestPart(value = "testimonialImage", required = false) MultipartFile testimonialImage) throws IOException {

        Testimonials testimonial = new Testimonials();
        testimonial.setTestimonialName(testimonialName);
        testimonial.setExam(exam);
        testimonial.setPost(post);
        testimonial.setDescription(description);
        testimonial.setTestimonialColor(testimonialColor);

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
