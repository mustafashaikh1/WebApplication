package com.WebApplication.Controller;

import com.WebApplication.Entity.FacilityTitle;
import com.WebApplication.Service.FacilityTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FacilityTitleController {

    @Autowired
    private FacilityTitleService facilityTitleService;

    @PostMapping("/createFacilityTitle")
    public ResponseEntity<FacilityTitle> createFacilityTitle(@RequestBody FacilityTitle facilityTitle) {
        return ResponseEntity.ok(facilityTitleService.saveFacilityTitle(facilityTitle));
    }

    @GetMapping("/getAllFacilityTitle")
    public ResponseEntity<List<FacilityTitle>> getAllFacilityTitle() {
        return ResponseEntity.ok(facilityTitleService.getAllFacilityTitles());
    }

    @GetMapping("/getFacilityTitleById/{id}")
    public ResponseEntity<FacilityTitle> getFacilityTitleById(@PathVariable Long id) {
        Optional<FacilityTitle> facilityTitle = facilityTitleService.getFacilityTitleById(id);
        return facilityTitle.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/updateFacilityTitle/{id}")
    public ResponseEntity<FacilityTitle> updateFacilityTitle(@PathVariable Long id, @RequestBody FacilityTitle facilityTitle) {
        return ResponseEntity.ok(facilityTitleService.updateFacilityTitle(id, facilityTitle));
    }

    @DeleteMapping("/deleteFacilityTitle/{id}")
    public ResponseEntity<Void> deleteFacilityTitle(@PathVariable Long id) {
        facilityTitleService.deleteFacilityTitle(id);
        return ResponseEntity.noContent().build();
    }
}