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
    public ResponseEntity<?> createFacility(
            @RequestParam("facilityName") String facilityName,
            @RequestParam("experienceInYear") Byte experienceInYear,
            @RequestParam("subject") String subject,
            @RequestParam("facilityEducation") String facilityEducation,
            @RequestParam("institutecode") String institutecode,
//            @RequestParam("facilityColor") String facilityColor,
            @RequestParam(value = "facilityImage", required = false) MultipartFile facilityImage) throws IOException {

        if (institutecode == null || institutecode.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Institutecode is required and cannot be empty.");
        }

        Facility facility = new Facility();
        facility.setFacilityName(facilityName);
        facility.setExperienceInYear(experienceInYear);
        facility.setSubject(subject);
        facility.setFacilityEducation(facilityEducation);
//        facility.setFacilityColor(facilityColor);

        Facility savedFacility = facilityService.saveFacility(facility, institutecode, facilityImage);
        return ResponseEntity.ok(savedFacility);
    }

    // Update an existing Facility with an image upload
    @PutMapping("/updateFacility/{id}")
    public ResponseEntity<?> updateFacility(
            @PathVariable("id") Long id,
            @RequestParam("facilityName") String facilityName,
            @RequestParam("experienceInYear") Byte experienceInYear,
            @RequestParam("subject") String subject,
            @RequestParam("facilityEducation") String facilityEducation,
//            @RequestParam("facilityColor") String facilityColor,
            @RequestParam(value = "facilityImage", required = false) MultipartFile facilityImage) throws IOException {




        Facility facility = new Facility();
        facility.setFacilityName(facilityName);
        facility.setExperienceInYear(experienceInYear);
        facility.setSubject(subject);
        facility.setFacilityEducation(facilityEducation);
//        facility.setFacilityColor(facilityColor);

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
    public ResponseEntity<?> getAllFacilities(@RequestParam("institutecode") String institutecode) {
        if (institutecode == null || institutecode.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Institutecode is required and cannot be empty.");
        }

        List<Facility> facilities = facilityService.getAllFacilities(institutecode);
        return ResponseEntity.ok(facilities);
    }

    // Delete Facility by ID
    @DeleteMapping("/deleteFacility/{id}")
    public ResponseEntity<String> deleteFacility(@PathVariable("id") Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.ok("Facility deleted successfully.");
    }



    @PostMapping("/addFacilityColor")
    public ResponseEntity<String> addFacilityColor(
            @RequestParam String institutecode,
            @RequestParam String facilityColor) {

        facilityService.addFacilityColorByInstitutecode(institutecode, facilityColor);
        return ResponseEntity.ok("Facility color updated successfully for all facilities with institutecode " + institutecode);
    }

    @PutMapping("/updateFacilityColor")  // Use PUT instead of POST for updating
    public ResponseEntity<String> updateFacilityColor(
            @RequestParam String institutecode,
            @RequestParam String facilityColor) {

        facilityService.updateFacilityColorByInstitutecode(institutecode, facilityColor);
        return ResponseEntity.ok("Facility color updated successfully for institutecode: " + institutecode);
    }

    @DeleteMapping("/deleteFacilityColor")
    public ResponseEntity<String> deleteFacilityColor(
            @RequestParam String institutecode) {

        facilityService.deleteFacilityColorByInstitutecode(institutecode);
        return ResponseEntity.ok("Facility color deleted successfully for institutecode: " + institutecode);
    }

}
