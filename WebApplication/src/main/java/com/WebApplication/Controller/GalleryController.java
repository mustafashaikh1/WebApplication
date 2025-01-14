package com.WebApplication.Controller;

import com.WebApplication.Entity.Gallery;
import com.WebApplication.Service.GalleryService;
import com.WebApplication.Service.CloudinaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "https://pjsofttech.in")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/createGallery")
    public ResponseEntity<?> createGallery(@RequestParam String institutecode,
                                           @RequestParam String eventName,
                                           @RequestParam Integer year,
                                           @RequestParam String galleryColor,
                                           @RequestParam MultipartFile galleryImage) throws IOException {
        // Check if a gallery already exists for the given institutecode
        Optional<Gallery> existingGallery = galleryService.getGalleryByInstitutecode(institutecode);
        if (existingGallery.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A gallery already exists for the given institutecode.");
        }

        String imageUrl = cloudinaryService.uploadImage(galleryImage);

        Gallery gallery = new Gallery();
        gallery.setEventName(eventName);
        gallery.setYear(year);
        gallery.setInstitutecode(institutecode);
        gallery.setGalleryImage(imageUrl);  // Set the URL of the uploaded image
        gallery.setGalleryColor(galleryColor);

        return ResponseEntity.status(HttpStatus.CREATED).body(galleryService.createGallery(gallery, institutecode));
    }

    @PutMapping("/updateGallery/{id}")
    public ResponseEntity<?> updateGallery(@PathVariable Long id,
                                           @RequestParam String eventName,
                                           @RequestParam Integer year,
                                           @RequestParam String galleryColor,
                                           @RequestParam(required = false) MultipartFile galleryImage) throws IOException {
        Gallery gallery = galleryService.getGalleryById(id);

        if (gallery == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gallery not found for the given ID.");
        }

        if (galleryImage != null) {
            String imageUrl = cloudinaryService.uploadImage(galleryImage);
            gallery.setGalleryImage(imageUrl);  // Update the image URL
        }

        gallery.setEventName(eventName);
        gallery.setYear(year);
        gallery.setGalleryColor(galleryColor);

        // Save the updated gallery
        return ResponseEntity.ok(galleryService.updateGallery(id, gallery));
    }

    @DeleteMapping("/deleteGallery/{id}")
    public ResponseEntity<String> deleteGallery(@PathVariable Long id) {
        Gallery gallery = galleryService.getGalleryById(id);

        if (gallery == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gallery not found for the given ID.");
        }

        galleryService.deleteGallery(id);
        return ResponseEntity.ok("Gallery deleted successfully.");
    }

    @GetMapping("/getGalleryById/{id}")
    public ResponseEntity<?> getGalleryById(@PathVariable Long id) {
        Gallery gallery = galleryService.getGalleryById(id);

        if (gallery == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gallery not found for the given ID.");
        }

        return ResponseEntity.ok(gallery);
    }

    @GetMapping("/getAllGalleries")
    public ResponseEntity<?> getAllGalleries(@RequestParam String institutecode) {
        List<Gallery> galleries = galleryService.getAllGalleries(institutecode);

        if (galleries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No galleries found for the given institutecode.");
        }

        return ResponseEntity.ok(galleries);
    }
}
