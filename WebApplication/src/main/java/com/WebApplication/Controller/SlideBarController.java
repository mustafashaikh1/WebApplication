package com.WebApplication.Controller;

import com.WebApplication.Entity.SlideBar;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.SlideBarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class SlideBarController {

    @Autowired
    private SlideBarService slideBarService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/createSlideBar")
    public ResponseEntity<?> createSlideBar(@RequestParam String slideBarColor,
                                            @RequestParam String institutecode,
                                            @RequestParam(required = false) List<MultipartFile> slideImages) {
        try {
            if (slideBarService.existsByInstitutecode(institutecode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A SlideBar with the given institutecode already exists.");
            }

            SlideBar slideBar = new SlideBar();
            slideBar.setSlideBarColor(slideBarColor);
            slideBar.setInstitutecode(institutecode);

            // Upload multiple images
            if (slideImages != null && !slideImages.isEmpty()) {
                List<String> imageUrls = new ArrayList<>();
                for (MultipartFile slideImage : slideImages) {
                    String imageUrl = cloudinaryService.uploadImage(slideImage);
                    imageUrls.add(imageUrl);
                }
                slideBar.setSlideImages(imageUrls);
            }

            SlideBar createdSlideBar = slideBarService.createSlideBar(slideBar, institutecode, slideImages);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSlideBar);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create SlideBar: " + e.getMessage());
        }
    }



    @PutMapping("/updateSlideBar")
    public ResponseEntity<?> updateSlideBarByInstitutecode(
            @RequestParam String institutecode,
            @RequestParam(required = false) String slideBarColor,
            @RequestParam(required = false) List<MultipartFile> slideImages) {
        try {
            // Create a SlideBar object for the update
            SlideBar updatedSlideBar = new SlideBar();

            // Set the slideBarColor if provided
            if (slideBarColor != null) {
                updatedSlideBar.setSlideBarColor(slideBarColor);
            }

            // Call the service to update the SlideBar
            SlideBar result = slideBarService.updateSlideBarByInstitutecode(institutecode, updatedSlideBar, slideImages);

            // Return the updated SlideBar
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating SlideBar: " + e.getMessage());
        }
    }



    @DeleteMapping("/deleteSlideBar/{id}")
    public ResponseEntity<String> deleteSlideBar(@PathVariable Long id) {
        slideBarService.deleteSlideBar(id);
        return ResponseEntity.ok("SlideBar deleted successfully.");
    }

    @GetMapping("/getSlideBarByInstitutecode")
    public Optional<SlideBar> getSlideBarByInstitutecode(@RequestParam String institutecode) {
        return slideBarService.getSlideBarByInstitutecode(institutecode);
    }

    @GetMapping("/getAllSlideBars")
    public ResponseEntity<List<SlideBar>> getAllSlideBars() {
        return ResponseEntity.ok(slideBarService.getAllSlideBars());
    }
}