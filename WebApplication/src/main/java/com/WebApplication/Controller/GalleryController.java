package com.WebApplication.Controller;


import com.WebApplication.Entity.Gallery;
import com.WebApplication.Service.GalleryService;
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
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @PostMapping("/createGallery")
    public ResponseEntity<?> createGallery(@RequestParam String eventName,
                                           @RequestParam Integer year,
                                           @RequestParam String galleryColor,
                                           @RequestParam String institutecode,
                                           @RequestParam(required = false) List<MultipartFile> galleryImages) {
        try {
            Gallery gallery = new Gallery();
            gallery.setEventName(eventName);
            gallery.setYear(year);
            gallery.setGalleryColor(galleryColor);
            gallery.setInstitutecode(institutecode);

            Gallery createdGallery = galleryService.createGallery(gallery, institutecode, galleryImages);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGallery);
        } catch (IOException e) {
            log.error("Failed to create Gallery: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create Gallery: " + e.getMessage());
        }
    }

    // ✅ Update Gallery without deleting previous images
    @PutMapping("/updateGallery")
    public ResponseEntity<?> updateGalleryByGalleryIdAndInstitutecode(
            @RequestParam Long galleryId,
            @RequestParam String institutecode,
            @RequestParam(required = false) List<MultipartFile> galleryImages,
            @RequestParam(required = false) String galleryColor) {
        try {
            Gallery updatedGallery = galleryService.updateGalleryByGalleryIdAndInstitutecode(
                    galleryId, institutecode, galleryImages, galleryColor);
            return ResponseEntity.ok(updatedGallery);
        } catch (RuntimeException e) {
            log.error("Gallery update failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gallery update failed: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error uploading images: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading images: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteGallery")
    public ResponseEntity<String> deleteGalleryByGalleryIdAndInstitutecode(
            @RequestParam Long galleryId,
            @RequestParam String institutecode) {
        try {
            galleryService.deleteGalleryByGalleryIdAndInstitutecode(galleryId, institutecode);
            return ResponseEntity.ok("Gallery deleted successfully.");
        } catch (RuntimeException e) {
            log.error("Gallery deletion failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gallery deletion failed: " + e.getMessage());
        }
    }

    @GetMapping("/getGalleryById/{id}")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable Long id) {
        return ResponseEntity.ok(galleryService.getGalleryById(id));
    }

    @GetMapping("/getAllGalleries")
    public ResponseEntity<List<Gallery>> getAllGalleries(@RequestParam String institutecode) {
        return ResponseEntity.ok(galleryService.getAllGalleries(institutecode));
    }
}
