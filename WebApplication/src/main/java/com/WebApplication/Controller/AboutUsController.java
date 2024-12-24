package com.WebApplication.Controller;

import com.WebApplication.Entity.AboutUs;
import com.WebApplication.Service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class AboutUsController {

    @Autowired
    private AboutUsService aboutUsService;

    @PostMapping("/createAboutUs")
    public ResponseEntity<AboutUs> createAboutUs(
            @RequestParam String institutecode,
            @RequestParam  String aboutUsTitle,
            @RequestParam String description,
            @RequestPart(value = "aboutUsImage", required = false)
            MultipartFile aboutUsImage) { // Accept MultipartFile for image

        AboutUs aboutUs = new AboutUs();
        aboutUs.setAboutUsTitle(aboutUsTitle);
        aboutUs.setDescription(description);

        AboutUs createdAboutUs = aboutUsService.createAboutUs(aboutUs, institutecode, aboutUsImage);

        return ResponseEntity.ok(createdAboutUs);
    }


    @PutMapping("/updateAboutUs/{id}")
    public ResponseEntity<AboutUs> updateAboutUs(
            @PathVariable Long id,
            @RequestParam String aboutUsTitle,
            @RequestParam  String description,
            @RequestPart(value = "aboutUsImage", required = false)
            MultipartFile aboutUsImage) {


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
    public ResponseEntity<List<AboutUs>> getAllAboutUs(@RequestParam String institutecode) {
        return ResponseEntity.ok(aboutUsService.getAllAboutUs(institutecode));
    }
}
