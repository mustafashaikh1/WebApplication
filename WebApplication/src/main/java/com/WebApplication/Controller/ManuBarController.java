package com.WebApplication.Controller;

import com.WebApplication.Entity.ManuBar;
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

@RestController
@CrossOrigin(origins = {
        "https://pjsofttech.in",
        "https://live.ooacademy.co.in",
        "https://course.yashodapublication.com"
})
public class ManuBarController {

    @Autowired
    private ManuBarService manuBarService;

    @PostMapping("/createManuBar")
    public ResponseEntity<?> createManuBar(@RequestParam String manuBarColor,
                                           @RequestParam String institutecode,
                                           @RequestParam(required = false) MultipartFile menubarImage) {
        if (institutecode == null || institutecode.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Institutecode is required to create ManuBar.");
        }

        if (manuBarService.existsByInstitutecode(institutecode)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A ManuBar with the given institutecode already exists.");
        }

        ManuBar manuBar = new ManuBar();
        manuBar.setManuBarColor(manuBarColor);
        manuBar.setInstitutecode(institutecode);

        ManuBar createdManuBar = manuBarService.createManuBar(manuBar, institutecode, menubarImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdManuBar);
    }

    @PutMapping("/updateManuBarByInstitutecode")
    public ResponseEntity<?> updateManuBarByInstitutecode(@RequestParam String institutecode,
                                                          @RequestParam String manuBarColor,
                                                          @RequestParam(required = false) MultipartFile menubarImage) {
        if (institutecode == null || institutecode.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Institutecode is required to update ManuBar.");
        }

        try {
            ManuBar updatedManuBar = new ManuBar();
            updatedManuBar.setManuBarColor(manuBarColor);

            ManuBar result = manuBarService.updateManuBarByInstitutecode(institutecode, updatedManuBar, menubarImage);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating ManuBar: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteManuBar/{id}")
    public ResponseEntity<String> deleteManuBar(@PathVariable Long id) {
        manuBarService.deleteManuBar(id);
        return ResponseEntity.ok("ManuBar deleted successfully.");
    }

    @GetMapping("/getManuBarByInstitutecode")
    public ResponseEntity<?> getManuBarByInstitutecode(@RequestParam String institutecode) {
        if (institutecode == null || institutecode.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Institutecode is required to fetch ManuBar.");
        }

        Optional<ManuBar> manuBar = manuBarService.getManuBarByInstitutecode(institutecode);
        if (manuBar.isPresent()) {
            return ResponseEntity.ok(manuBar);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No ManuBar found for the given institutecode.");
        }
    }

    @GetMapping("/getAllManuBars")
    public ResponseEntity<List<ManuBar>> getAllManuBars() {
        return ResponseEntity.ok(manuBarService.getAllManuBars());
    }
}
