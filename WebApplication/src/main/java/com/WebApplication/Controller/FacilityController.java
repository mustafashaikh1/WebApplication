package com.WebApplication.Controller;

import com.WebApplication.Entity.Facility;
import com.WebApplication.Service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "https://pjsofttech.in")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @PostMapping("/createFacility")
    public ResponseEntity<Facility> createFacility( @RequestBody Facility facility,
                                                    @RequestParam String institutecode)

            {
        Facility savedFacility = facilityService.saveFacility(facility, institutecode);
        return ResponseEntity.ok(savedFacility);
    }

    @PutMapping("/updateFacility/{id}")
    public ResponseEntity<Facility> updateFacility(
            @PathVariable("id") Long id,
            @RequestBody Facility facility) {
        Facility updatedFacility = facilityService.updateFacility(id, facility);
        return ResponseEntity.ok(updatedFacility);
    }


    @GetMapping("/getFacilityById/{id}")
    public ResponseEntity<Facility> getFacilityById(@PathVariable("id") Long id) {
        Facility facility = facilityService.getFacilityById(id);
        return ResponseEntity.ok(facility);
    }

    @GetMapping("/getAllFacilities")
    public ResponseEntity<List<Facility>> getAllFacilities(
            @RequestParam String institutecode) {
        List<Facility> facilities = facilityService.getAllFacilities(institutecode);
        return ResponseEntity.ok(facilities);
    }

    @DeleteMapping("/deleteFacility/{id}")
    public ResponseEntity<String> deleteFacility(@PathVariable("id") Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.ok("Facility deleted successfully.");
    }
}
