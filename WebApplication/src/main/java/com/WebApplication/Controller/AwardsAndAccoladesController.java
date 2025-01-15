package com.WebApplication.Controller;

import com.WebApplication.Entity.AwardsAndAccolades;
import com.WebApplication.Service.AwardsAndAccoladesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class AwardsAndAccoladesController {

    @Autowired
    private AwardsAndAccoladesService awardsAndAccoladesService;

    @PostMapping("/createAward")
    public ResponseEntity<?> createAward(
            @RequestParam String institutecode,
            @RequestParam String awardName,
            @RequestParam String description,
            @RequestParam String awardedBy,
            @RequestParam String awardTo,
            @RequestParam int year,
            @RequestPart(value = "awardImage", required = false) MultipartFile awardImage) {

        AwardsAndAccolades award = new AwardsAndAccolades();
        award.setAwardName(awardName);
        award.setDescription(description);
        award.setAwardedBy(awardedBy);
        award.setAwardTo(awardTo);
        award.setYear(year);

        try {
            AwardsAndAccolades createdAward = awardsAndAccoladesService.saveAward(award, institutecode, awardImage);
            return ResponseEntity.ok(createdAward);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409 Conflict for existing record
        }
    }

    @PutMapping("/updateAward/{id}")
    public ResponseEntity<?> updateAward(
            @PathVariable Long id,
            @RequestParam String awardName,
            @RequestParam String description,
            @RequestParam String awardedBy,
            @RequestParam String awardTo,
            @RequestParam int year,
            @RequestParam String institutecode,
            @RequestPart(value = "awardImage", required = false) MultipartFile awardImage) {

        AwardsAndAccolades award = new AwardsAndAccolades();
        award.setAwardName(awardName);
        award.setDescription(description);
        award.setAwardedBy(awardedBy);
        award.setAwardTo(awardTo);
        award.setYear(year);
        award.setInstitutecode(institutecode);

        try {
            AwardsAndAccolades updatedAward = awardsAndAccoladesService.updateAward(id, award, awardImage);
            return ResponseEntity.ok(updatedAward);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // 404 Not Found for non-existent record
        }
    }

    @DeleteMapping("/deleteAward/{id}")
    public ResponseEntity<?> deleteAward(@PathVariable Long id) {
        try {
            awardsAndAccoladesService.deleteAward(id);
            return ResponseEntity.ok("Award deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // 404 Not Found for non-existent record
        }
    }

    @GetMapping("/getAward/{id}")
    public ResponseEntity<?> getAwardById(@PathVariable Long id) {
        try {
            AwardsAndAccolades award = awardsAndAccoladesService.getAwardById(id);
            return ResponseEntity.ok(award);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // 404 Not Found for non-existent record
        }
    }

    @GetMapping("/getAllAwards")
    public ResponseEntity<?> getAllAwards(@RequestParam String institutecode) {
        List<AwardsAndAccolades> awards = awardsAndAccoladesService.getAllAwards(institutecode);
        return ResponseEntity.ok(awards);
    }
}
