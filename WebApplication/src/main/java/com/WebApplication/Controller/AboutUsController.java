package com.WebApplication.Controller;

import com.WebApplication.Entity.AboutUs;
import com.WebApplication.Service.AboutUsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class AboutUsController {

    @Autowired
    private AboutUsService aboutUsService;

    @PostMapping("/createAboutUs")
    public ResponseEntity<?> createAboutUs(
            @RequestParam String institutecode,
            @RequestParam String aboutUsTitle,
            @RequestParam String description,
            @RequestPart(value = "aboutUsImage", required = false) MultipartFile aboutUsImage) {

        AboutUs aboutUs = new AboutUs();
        aboutUs.setAboutUsTitle(aboutUsTitle);
        aboutUs.setDescription(description);

        try {
            AboutUs createdAboutUs = aboutUsService.createAboutUs(aboutUs, institutecode, aboutUsImage);
            return ResponseEntity.ok(createdAboutUs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409 Conflict for existing record
        }
    }

    @PutMapping("/updateAboutUs/{id}")
    public ResponseEntity<AboutUs> updateAboutUs(
            @PathVariable Long id,
            @RequestParam String aboutUsTitle,
            @RequestParam String description,
            @RequestPart(value = "aboutUsImage", required = false) MultipartFile aboutUsImage) {

        AboutUs aboutUs = new AboutUs();
        aboutUs.setAboutUsTitle(aboutUsTitle);
        aboutUs.setDescription(description);

        AboutUs updatedAboutUs = aboutUsService.updateAboutUs(id, aboutUs, aboutUsImage);
        return ResponseEntity.ok(updatedAboutUs);
    }

    @DeleteMapping("/deleteAboutUs/{id}")
    public ResponseEntity<String> deleteAboutUs(@PathVariable Long id) {
        aboutUsService.deleteAboutUs(id);
        return ResponseEntity.ok("AboutUs deleted successfully.");
    }

    @GetMapping("/getAboutUsById/{id}")
    public ResponseEntity<AboutUs> getAboutUsById(@PathVariable Long id) {
        return ResponseEntity.ok(aboutUsService.getAboutUsById(id));
    }

    @GetMapping("/getAllAboutUs")
    public ResponseEntity<Optional<AboutUs>> getAllAboutUs(@RequestParam String institutecode) {
        return ResponseEntity.ok(aboutUsService.getAllAboutUs(institutecode));
    }
}
