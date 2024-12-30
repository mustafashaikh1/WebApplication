package com.WebApplication.Controller;

import com.WebApplication.Entity.Facility;
import com.WebApplication.Service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @PostMapping("/createFacility")
    public ResponseEntity<?> createFacility(@RequestParam String facilityName,
                                            @RequestParam String institutecode,
                                            @RequestParam int experienceInYear,
                                            @RequestParam String facilityEducation,
                                            @RequestParam String subject,
                                            @RequestParam(required = false) MultipartFile facilityImage) {
        try {
            if (facilityService.existsByInstitutecode(institutecode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A Facility with the given institutecode already exists.");
            }

            Facility facility = new Facility();
            facility.setFacilityName(facilityName);
            facility.setInstitutecode(institutecode);
            facility.setExperienceInYear((byte) experienceInYear);
            facility.setFacilityEducation(facilityEducation);
            facility.setSubject(subject);

            Facility createdFacility = facilityService.saveFacility(facility, institutecode, facilityImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFacility);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload facility image: " + e.getMessage());
        }
    }

    @PutMapping("/updateFacilityByInstitutecode")
    public ResponseEntity<?> updateFacilityByInstitutecode(@RequestParam String institutecode,
                                                           @RequestParam String facilityName,
                                                           @RequestParam String subject,
                                                           @RequestParam String facilityEducation,
                                                           @RequestParam int experienceInYear,
                                                           @RequestParam(required = false) MultipartFile facilityImage) {
        try {
            Facility updatedFacility = new Facility();
            updatedFacility.setFacilityName(facilityName);
            updatedFacility.setSubject(subject);
            updatedFacility.setFacilityEducation(facilityEducation);
            updatedFacility.setExperienceInYear((byte) experienceInYear);

            Facility result = facilityService.updateFacilityByInstitutecode(institutecode, updatedFacility, facilityImage);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload facility image: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteFacility/{id}")
    public ResponseEntity<String> deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.ok("Facility deleted successfully.");
    }

    @GetMapping("/getFacilityByInstitutecode")
    public ResponseEntity<Facility> getFacilityByInstitutecode(@RequestParam String institutecode) {
        return facilityService.getFacilityByInstitutecode(institutecode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/getAllFacilities")
    public ResponseEntity<Optional<Facility>> getAllFacilities(@RequestParam String institutecode) {
        return ResponseEntity.ok(facilityService.getAllFacilities(institutecode));
    }
}
