package com.WebApplication.Controller;

import com.WebApplication.Entity.MapAndImages;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.MapAndImagesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class MapAndImagesController {

    @Autowired
    private MapAndImagesService mapAndImagesService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/createMapAndImages")
    public ResponseEntity<?> createMapAndImages(
            @Valid
            @RequestParam String institutecode,
            @RequestParam MultipartFile contactImage,
            @RequestParam String maps) throws IOException {
        try {
            // Check if MapAndImages already exists for the institutecode
            if (mapAndImagesService.existsByInstitutecode(institutecode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A MapAndImages entry with the given institutecode already exists.");
            }

            // Create a new MapAndImages instance
            MapAndImages mapAndImages = new MapAndImages();
            mapAndImages.setInstitutecode(institutecode);
            mapAndImages.setMaps(maps);

            // Save the MapAndImages entry with the uploaded contact image
            MapAndImages createdMapAndImages = mapAndImagesService.createMapAndImages(mapAndImages, institutecode, contactImage);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdMapAndImages);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading contact image: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating MapAndImages: " + e.getMessage());
        }
    }




    @PutMapping("/updateMapAndImagesByInstitutecode")
    public ResponseEntity<?> updateMapAndImagesByInstitutecode(
            @RequestParam String institutecode,
            @RequestParam(required = false) String maps,
            @RequestParam(required = false) MultipartFile contactImage) {
        try {
            // Create a MapAndImages object for the update
            MapAndImages updatedMapAndImages = new MapAndImages();

            // Set the new values for fields if provided
            if (maps != null) {
                updatedMapAndImages.setMaps(maps);
            }

            // Handle the contactImage update
            if (contactImage != null && !contactImage.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(contactImage);
                updatedMapAndImages.setContactImage(imageUrl);
            }

            // Call the service to update the MapAndImages
            MapAndImages result = mapAndImagesService.updateMapAndImagesByInstitutecode(institutecode, updatedMapAndImages, contactImage);

            // Return the updated MapAndImages
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MapAndImages entry not found: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating MapAndImages: " + e.getMessage());
        }
    }


    @GetMapping("/getMapAndImagesByInstitutecode")
    public ResponseEntity<MapAndImages> getMapAndImagesByInstitutecode(@RequestParam String institutecode) {
        return mapAndImagesService.getMapAndImagesByInstitutecode(institutecode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllMapAndImages")
    public ResponseEntity<List<MapAndImages>> getAllMapAndImages() {
        return ResponseEntity.ok(mapAndImagesService.getAllMapAndImages());
    }

    @DeleteMapping("/deleteMapAndImages")
    public ResponseEntity<Void> deleteMapAndImages(@RequestParam String institutecode) {
        mapAndImagesService.deleteMapAndImages(institutecode);
        return ResponseEntity.noContent().build();
    }
}
