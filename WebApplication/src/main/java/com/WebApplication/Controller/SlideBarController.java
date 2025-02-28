package com.WebApplication.Controller;

import com.WebApplication.Entity.SlideBar;
import com.WebApplication.Service.SlideBarService;
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
public class SlideBarController {

    @Autowired
    private SlideBarService slideBarService;

    // ✅ Create SlideBar
    @PostMapping("/createSlideBar")
    public ResponseEntity<?> createSlideBar(@RequestParam String slideBarColor,
                                            @RequestParam String institutecode,
                                            @RequestParam(required = false) List<MultipartFile> slideImages) {
        try {
            SlideBar slideBar = new SlideBar();
            slideBar.setSlideBarColor(slideBarColor);
            slideBar.setInstitutecode(institutecode);

            SlideBar createdSlideBar = slideBarService.createSlideBar(slideBar, institutecode, slideImages);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSlideBar);
        } catch (IOException e) {
            log.error("Failed to create SlideBar: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create SlideBar: " + e.getMessage());
        }
    }


    // ✅ Update SlideBar by ImageUrlId and Institutecode (Without Deleting Previous Images)
    @PutMapping("/updateByImageUrlIdAndInstitutecode")
    public ResponseEntity<?> updateSlideBarByImageUrlIdAndInstitutecode(
            @RequestParam Long imageUrlId,
            @RequestParam String institutecode,
            @RequestParam(required = false) List<MultipartFile> slideImages,
            @RequestParam(required = false) String slideBarColor) {
        try {
            SlideBar updatedSlideBar = slideBarService.updateSlideBarByImageUrlIdAndInstitutecode(imageUrlId, institutecode, slideImages, slideBarColor);
            return ResponseEntity.ok(updatedSlideBar);
        } catch (RuntimeException e) {
            log.error("SlideBar update failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SlideBar update failed: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error uploading images: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading images: " + e.getMessage());
        }
    }

    // ✅ Delete SlideBar by ImageUrlId and Institutecode
    @DeleteMapping("/deleteSlideBar")
    public ResponseEntity<String> deleteSlideBarByImageUrlIdAndInstitutecode(
            @RequestParam Long imageUrlId,
            @RequestParam String institutecode) {
        try {
            slideBarService.deleteSlideBarByImageUrlIdAndInstitutecode(imageUrlId, institutecode);
            return ResponseEntity.ok("Image URL ID and associated file deleted successfully.");
        } catch (RuntimeException e) {
            log.error("SlideBar deletion failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SlideBar deletion failed: " + e.getMessage());
        }
    }

    // ✅ Get all SlideBars by Institutecode
    @GetMapping("/getAllSlideBars")
    public ResponseEntity<Optional<SlideBar>> getAllSlideBarsByInstitutecode(@RequestParam String institutecode) {
        Optional<SlideBar> slideBars = slideBarService.getAllSlideBarsByInstitutecode(institutecode);
        return ResponseEntity.ok(slideBars);
    }
}
