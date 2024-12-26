package com.WebApplication.Controller;

import com.WebApplication.Entity.ManuBar;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.ManuBarService;
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
@CrossOrigin(origins = "http://localhost:3000/")
public class ManuBarController {

    @Autowired
    private ManuBarService manuBarService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/createManuBar")
    public ResponseEntity<?> createManuBar(@RequestParam String manuBarColor,
                                           @RequestParam String institutecode,
                                           @RequestParam(required = false) MultipartFile menubarImage,
                                           @RequestParam List<String> menuItems) {
        try {
            // Check if a ManuBar with the given institutecode already exists
            if (manuBarService.existsByInstitutecode(institutecode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A ManuBar with the given institutecode already exists.");
            }

            // Create a new ManuBar object
            ManuBar manuBar = new ManuBar();
            manuBar.setManuBarColor(manuBarColor);
            manuBar.setInstitutecode(institutecode);
            manuBar.setMenuItems(menuItems);

            // Handle image upload if provided
            if (menubarImage != null && !menubarImage.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(menubarImage); // Upload image to Cloudinary
                manuBar.setMenubarImage(imageUrl); // Set the image URL to the entity
            }

            // Save and return the created ManuBar
            ManuBar createdManuBar = manuBarService.createManuBar(manuBar, institutecode, menubarImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdManuBar);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create ManuBar: " + e.getMessage());
        }
    }



    @PutMapping("/updateManuBarByInstitutecode")
    public ResponseEntity<?> updateManuBarByInstitutecode(@RequestParam String institutecode,
                                                          @RequestParam String manuBarColor,
                                                          @RequestParam List<String> menuItems,
                                                          @RequestParam(required = false) MultipartFile menubarImage) {
        try {
            // Create a temporary ManuBar object to hold the updated data
            ManuBar updatedManuBar = new ManuBar();
            updatedManuBar.setManuBarColor(manuBarColor);
            updatedManuBar.setMenuItems(menuItems);

            // Call the service method to update the ManuBar
            ManuBar result = manuBarService.updateManuBarByInstitutecode(institutecode, updatedManuBar, menubarImage);

            // Return the updated ManuBar in the response
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating ManuBar: " + e.getMessage());
        }
    }





    @DeleteMapping("/deleteManuBar/{id}")
    public ResponseEntity<String> deleteManuBar(@PathVariable Long id) {
        manuBarService.deleteManuBar(id);
        return ResponseEntity.ok("ManuBar deleted successfully.");
    }




    @GetMapping("/by-institutecode")
    public Optional<ManuBar> getManuBarByInstitutecode(@RequestParam String institutecode) {
        return manuBarService.getManuBarByInstitutecode(institutecode);
    }



    @GetMapping("/getAllManuBars")
    public ResponseEntity<List<ManuBar>> getAllManuBars() {
        return ResponseEntity.ok(manuBarService.getAllManuBars());
    }


}