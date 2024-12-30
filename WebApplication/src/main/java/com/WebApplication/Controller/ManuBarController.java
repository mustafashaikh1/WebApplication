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

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class ManuBarController {

    @Autowired
    private ManuBarService manuBarService;

    @PostMapping("/createManuBar")
    public ResponseEntity<?> createManuBar(@RequestParam String manuBarColor,
                                           @RequestParam String institutecode,
                                           @RequestParam(required = false) MultipartFile menubarImage) {
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

    @GetMapping("/by-institutecode")
    public ResponseEntity<Optional<ManuBar>> getManuBarByInstitutecode(@RequestParam String institutecode) {
        return ResponseEntity.ok(manuBarService.getManuBarByInstitutecode(institutecode));
    }

    @GetMapping("/getAllManuBars")
    public ResponseEntity<List<ManuBar>> getAllManuBars() {
        return ResponseEntity.ok(manuBarService.getAllManuBars());
    }
}
