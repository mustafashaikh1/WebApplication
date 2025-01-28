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
@CrossOrigin(origins = "https://pjsofttech.in")
public class SlideBarController {

    @Autowired
    private SlideBarService slideBarService;

    @Autowired
    private CloudinaryService cloudinaryService;

    // Create SlideBar
    @PostMapping("/createSlideBar")
    public ResponseEntity<?> createSlideBar(@RequestParam String slideBarColor,
                                            @RequestParam String institutecode,
                                            @RequestParam(required = false) List<MultipartFile> slideImages) {
        try {
            // Create SlideBar object
            SlideBar slideBar = new SlideBar();
            slideBar.setSlideBarColor(slideBarColor);
            slideBar.setInstitutecode(institutecode);

            // Call service to create SlideBar
            SlideBar createdSlideBar = slideBarService.createSlideBar(slideBar, institutecode, slideImages);

            // Return created SlideBar
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSlideBar);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create SlideBar: " + e.getMessage());
        }
    }




    // Update SlideBar by ImageUrlId and Institutecode (Update particular image)
    @PutMapping("/updateByImageUrlIdAndInstitutecode")
    public ResponseEntity<?> updateSlideBarByImageUrlIdAndInstitutecode(
            @RequestParam Long imageUrlId,
            @RequestParam String institutecode,
            @RequestParam(required = false) List<MultipartFile> slideImages,
            @RequestParam(required = false) String slideBarColor) {  // Add slideBarColor parameter
        try {
            // Call service to update SlideBar by ImageUrlId and Institutecode
            SlideBar updatedSlideBar = slideBarService.updateSlideBarByImageUrlIdAndInstitutecode(imageUrlId, institutecode, slideImages, slideBarColor);

            // Return updated SlideBar
            return ResponseEntity.ok(updatedSlideBar);
        } catch (RuntimeException e) {
            log.error("SlideBar update failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("SlideBar update failed: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error uploading images: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading images: " + e.getMessage());
        }
    }




    @DeleteMapping("/deleteSlideBar")
    public ResponseEntity<String> deleteSlideBarByImageUrlIdAndInstitutecode(
            @RequestParam Long imageUrlId,
            @RequestParam String institutecode) {
        slideBarService.deleteSlideBarByImageUrlIdAndInstitutecode(imageUrlId, institutecode);
        return ResponseEntity.ok("Image URL ID and associated file deleted successfully.");
    }



    @GetMapping("/getAllSlideBars")
    public ResponseEntity<Optional<SlideBar>> getAllSlideBarsByInstitutecode(@RequestParam String institutecode) {
        // Call service to get SlideBars by institutecode
        Optional<SlideBar> slideBars = slideBarService.getAllSlideBarsByInstitutecode(institutecode);
        return ResponseEntity.ok(slideBars);
    }



//    @GetMapping("/getAllSlideBars")
//    public ResponseEntity<List<SlideBar>> getAllSlideBars() {
//        return ResponseEntity.ok(slideBarService.getAllSlideBars());
//    }
    
    
}