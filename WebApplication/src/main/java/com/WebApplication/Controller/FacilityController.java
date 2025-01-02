package com.WebApplication.Controller;

import com.WebApplication.Entity.Facility;
import com.WebApplication.Service.FacilityService;
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
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    // Create a new Facility with an image upload
    @PostMapping("/createFacility")
    public ResponseEntity<Facility> createFacility(
            @RequestParam("facilityName") String facilityName,
            @RequestParam("experienceInYear") Byte experienceInYear,
            @RequestParam("subject") String subject,
            @RequestParam("facilityEducation") String facilityEducation,
            @RequestParam("institutecode") String institutecode,
            @RequestParam(value = "facilityImage", required = false) MultipartFile facilityImage) throws IOException {

        Facility facility = new Facility();
        facility.setFacilityName(facilityName);
        facility.setExperienceInYear(experienceInYear);
        facility.setSubject(subject);
        facility.setFacilityEducation(facilityEducation);

        Facility savedFacility = facilityService.saveFacility(facility, institutecode, facilityImage);
        return ResponseEntity.ok(savedFacility);
    }

    // Update an existing Facility with an image upload
    @PutMapping("/updateFacility/{id}")
    public ResponseEntity<Facility> updateFacility(
            @PathVariable("id") Long id,
            @RequestParam("facility") String facilityName,
            @RequestParam("experienceInYear") Byte experienceInYear,
            @RequestParam("subject") String subject,
            @RequestParam("facilityEducation") String facilityEducation,
            @RequestParam(value = "facilityImage", required = false) MultipartFile facilityImage) throws IOException {

        Facility facility = new Facility();
        facility.setFacilityName(facilityName);
        facility.setExperienceInYear(experienceInYear);
        facility.setSubject(subject);
        facility.setFacilityEducation(facilityEducation);

        Facility updatedFacility = facilityService.updateFacility(id, facility, facilityImage);
        return ResponseEntity.ok(updatedFacility);
    }

    // Get Facility by ID
    @GetMapping("/getFacilityById/{id}")
    public ResponseEntity<Facility> getFacilityById(@PathVariable("id") Long id) {
        Facility facility = facilityService.getFacilityById(id);
        return ResponseEntity.ok(facility);
    }

    // Get all Facilities by institutecode
    @GetMapping("/getAllFacilities")
    public ResponseEntity<List<Facility>> getAllFacilities(
            @RequestParam("institutecode") String institutecode) {
        List<Facility> facilities = facilityService.getAllFacilities(institutecode);
        return ResponseEntity.ok(facilities);
    }

    // Delete Facility by ID
    @DeleteMapping("/deleteFacility/{id}")
    public ResponseEntity<String> deleteFacility(@PathVariable("id") Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.ok("Facility deleted successfully.");
    }
}
