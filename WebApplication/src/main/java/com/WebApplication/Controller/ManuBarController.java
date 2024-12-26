package com.WebApplication.Controller;

import com.WebApplication.Entity.ManuBar;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.ManuBarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@Slf4j
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
        // Create a new ManuBar object manually from request parameters
        ManuBar manuBar = new ManuBar();
        manuBar.setManuBarColor(manuBarColor);
        manuBar.setInstitutecode(institutecode);
        manuBar.setMenuItems(menuItems);

        // Handle image upload if provided
        if (menubarImage != null && !menubarImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(menubarImage); // Upload image to Cloudinary
            manuBar.setMenubarImage(imageUrl); // Set the image URL to the entity
        }

        return ResponseEntity.ok(manuBarService.createManuBar(manuBar, institutecode, menubarImage)); // Save and return the created ManuBar
    }


    @PutMapping("/updateManuBarByInstitutecode/{institutecode}")
    public ResponseEntity<ManuBar> updateManuBarByInstitutecode(@PathVariable String institutecode,
                                                                @RequestParam String manuBarColor,
                                                                @RequestParam List<String> menuItems,
                                                                @RequestParam(required = false) MultipartFile menubarImage) throws IOException {
        // Retrieve the existing ManuBar by institutecode
        ManuBar existingManuBar = (ManuBar) manuBarService.getManuBarByInstitutecode(institutecode);

        // Update fields from request parameters
        existingManuBar.setManuBarColor(manuBarColor);
        existingManuBar.setMenuItems(menuItems);

        // Handle image upload if provided
        if (menubarImage != null && !menubarImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(menubarImage); // Upload new image
            existingManuBar.setMenubarImage(imageUrl); // Update image URL
        }

        return ResponseEntity.ok(manuBarService.updateManuBarByInstitutecode(institutecode, existingManuBar)); // Save and return the updated ManuBar
    }




    @DeleteMapping("/deleteManuBar/{id}")
    public ResponseEntity<String> deleteManuBar(@PathVariable Long id) {
        manuBarService.deleteManuBar(id);
        return ResponseEntity.ok("ManuBar deleted successfully.");
    }




    @GetMapping("/by-institutecode")
    public List<ManuBar> getManuBarByInstitutecode(@RequestParam String institutecode) {
        return manuBarService.getManuBarByInstitutecode(institutecode);
    }



    @GetMapping("/getAllManuBars")
    public ResponseEntity<List<ManuBar>> getAllManuBars() {
        return ResponseEntity.ok(manuBarService.getAllManuBars());
    }


}
