package com.WebApplication.Controller;

import com.WebApplication.Entity.ManuBar;
import com.WebApplication.Service.ManuBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class ManuBarController {

    @Autowired
    private ManuBarService manuBarService;

    @PostMapping("/createManuBar")
    public ResponseEntity<ManuBar> createManuBar(@RequestBody ManuBar manuBar,
                                                 @RequestParam String institutecode,
                                                 @RequestParam(required = false) MultipartFile menubarImage) {
        return ResponseEntity.ok(manuBarService.createManuBar(manuBar, institutecode, menubarImage));
    }

    @PutMapping("/updateManuBar/{id}")
    public ResponseEntity<ManuBar> updateManuBar(@PathVariable Long id,
                                                 @RequestBody ManuBar manuBar) {
        return ResponseEntity.ok(manuBarService.updateManuBar(id, manuBar));
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
