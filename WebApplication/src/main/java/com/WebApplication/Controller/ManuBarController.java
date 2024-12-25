package com.WebApplication.Controller;

import com.WebApplication.Entity.ManuBar;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.ManuBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class ManuBarController {

    @Autowired
    private ManuBarService manuBarService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/createManuBar")
    public ResponseEntity<ManuBar> createManuBar(@RequestParam String manuBarColor,
                                                 @RequestParam String institutecode,
                                                 @RequestParam(required = false) MultipartFile menubarImage,
                                                 @RequestParam List<String> menuItems) throws IOException {
        // Creating the ManuBar object manually with the data from request parameters
        ManuBar manuBar = new ManuBar();
        manuBar.setManuBarColor(manuBarColor);
        manuBar.setInstitutecode(institutecode);
        manuBar.setMenuItems(menuItems);

        // Handle the image upload if provided
        if (menubarImage != null) {
            String imageUrl = cloudinaryService.uploadImage(menubarImage);
            manuBar.setImageUrl(imageUrl);
        }

        return ResponseEntity.ok(manuBarService.createManuBar(manuBar, institutecode, menubarImage));
    }

    @PutMapping("/updateManuBar/{id}")
    public ResponseEntity<ManuBar> updateManuBar(@PathVariable Long id,
                                                 @RequestParam String manuBarColor,
                                                 @RequestParam String institutecode,
                                                 @RequestParam List<String> menuItems,
                                                 @RequestParam(required = false) MultipartFile menubarImage) throws IOException {
        // Retrieving the existing ManuBar by ID
        ManuBar existingManuBar = manuBarService.getManuBarById(id);

        // Update fields from request parameters
        existingManuBar.setManuBarColor(manuBarColor);
        existingManuBar.setInstitutecode(institutecode);
        existingManuBar.setMenuItems(menuItems);

        // Handle the image upload if provided
        if (menubarImage != null) {
            String imageUrl = cloudinaryService.uploadImage(menubarImage); // Use Cloudinary service
            existingManuBar.setImageUrl(imageUrl);
        }

        return ResponseEntity.ok(manuBarService.updateManuBar(id, existingManuBar));
    }

    @DeleteMapping("/deleteManuBar/{id}")
    public ResponseEntity<String> deleteManuBar(@PathVariable Long id) {
        manuBarService.deleteManuBar(id);
        return ResponseEntity.ok("ManuBar deleted successfully.");
    }

    @GetMapping("/getManuBarById/{id}")
    public ResponseEntity<ManuBar> getManuBarById(@PathVariable Long id) {
        return ResponseEntity.ok(manuBarService.getManuBarById(id));
    }

    @GetMapping("/getAllManuBars")
    public ResponseEntity<List<ManuBar>> getAllManuBars(@RequestParam String institutecode) {
        return ResponseEntity.ok(manuBarService.getAllManuBars(institutecode));
    }
}
