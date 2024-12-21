package com.WebApplication.Controller;

import com.WebApplication.Entity.Gallery;
import com.WebApplication.Service.GalleryService;
import com.WebApplication.Service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/galleries")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/createGallery")
    public ResponseEntity<Gallery> createGallery(@RequestParam String institutecode,
                                                 @RequestParam String eventName,
                                                 @RequestParam Integer year,
                                                 @RequestParam MultipartFile galleryImage) throws IOException {
        String imageUrl = cloudinaryService.uploadImage(galleryImage);

        Gallery gallery = new Gallery();
        gallery.setEventName(eventName);
        gallery.setYear(year);
        gallery.setInstitutecode(institutecode);
        gallery.setGalleryImage(imageUrl);  // Set the URL of the uploaded image

        return ResponseEntity.ok(galleryService.createGallery(gallery, institutecode));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gallery> updateGallery(@PathVariable Long id,
                                                 @RequestParam String eventName,
                                                 @RequestParam Integer year,
                                                 @RequestParam(required = false) MultipartFile galleryImage) throws IOException {
        Gallery gallery = galleryService.getGalleryById(id);

        if (galleryImage != null) {
            String imageUrl = cloudinaryService.uploadImage(galleryImage);
            gallery.setGalleryImage(imageUrl);  // Update the image URL
        }

        gallery.setEventName(eventName);
        gallery.setYear(year);

        // Save the updated gallery
        return ResponseEntity.ok(galleryService.updateGallery(id, gallery));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGallery(@PathVariable Long id) {
        galleryService.deleteGallery(id);
        return ResponseEntity.ok("Gallery deleted successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable Long id) {
        return ResponseEntity.ok(galleryService.getGalleryById(id));
    }

    @GetMapping("/getAllGalleries")
    public ResponseEntity<List<Gallery>> getAllGalleries(@RequestParam String institutecode) {
        return ResponseEntity.ok(galleryService.getAllGalleries(institutecode));
    }
}
