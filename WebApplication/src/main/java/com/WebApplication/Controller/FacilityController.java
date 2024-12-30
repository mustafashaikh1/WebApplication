package com.WebApplication.Controller;

import com.WebApplication.Entity.Facility;
import com.WebApplication.Service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                            @RequestParam String subject) {
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

            Facility createdFacility = facilityService.saveFacility(facility, institutecode);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFacility);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Facility: " + e.getMessage());
        }
    }

    @PutMapping("/updateFacilityByInstitutecode")
    public ResponseEntity<?> updateFacilityByInstitutecode(@RequestParam String institutecode,
                                                           @RequestParam String facilityName,
                                                           @RequestParam String subject,
                                                           @RequestParam String facilityEducation,

                                                           @RequestParam int experienceInYear) {
        try {
            Facility updatedFacility = new Facility();
            updatedFacility.setFacilityName(facilityName);
            updatedFacility.setSubject(subject);
            updatedFacility.setFacilityEducation(facilityEducation);
            updatedFacility.setExperienceInYear((byte) experienceInYear);

            Facility result = facilityService.updateFacilityByInstitutecode(institutecode, updatedFacility);
            return ResponseEntity.ok(result);
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
