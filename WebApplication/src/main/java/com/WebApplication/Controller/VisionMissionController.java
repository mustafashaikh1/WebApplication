package com.WebApplication.Controller;

import com.WebApplication.Entity.VisionMission;
import com.WebApplication.Service.VisionMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class VisionMissionController {

    @Autowired
    private VisionMissionService visionMissionService;

    @PostMapping("/createVisionMission")
    public ResponseEntity<?> createVisionMission(@RequestParam String vision,
                                                 @RequestParam String mission,
                                                 @RequestParam String visionmissionColor,
                                                 @RequestParam String institutecode) {
        if (institutecode == null || institutecode.isEmpty()) {
            throw new RuntimeException("Institutecode is required.");
        }

        if (visionMissionService.existsByInstitutecode(institutecode)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A Vision and Mission with the given institutecode already exists.");
        }

        VisionMission visionMission = new VisionMission();
        visionMission.setVision(vision);
        visionMission.setMission(mission);
        visionMission.setVisionmissionColor(visionmissionColor);
        visionMission.setInstitutecode(institutecode);

        VisionMission createdVisionMission = visionMissionService.saveVisionMission(visionMission, institutecode);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVisionMission);
    }

    @PutMapping("/updateVisionMission")
    public ResponseEntity<?> updateVisionMission(@RequestParam String institutecode,
                                                 @RequestParam String vision,
                                                 @RequestParam String visionmissionColor,
                                                 @RequestParam String mission) {
        if (institutecode == null || institutecode.isEmpty()) {
            throw new RuntimeException("Institutecode is required.");
        }

        VisionMission updatedVisionMission = new VisionMission();
        updatedVisionMission.setVision(vision);
        updatedVisionMission.setMission(mission);
        updatedVisionMission.setVisionmissionColor(visionmissionColor);

        VisionMission result = visionMissionService.updateVisionMission(institutecode, updatedVisionMission);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteVisionMission")
    public ResponseEntity<String> deleteVisionMission(@RequestParam String institutecode) {
        if (institutecode == null || institutecode.isEmpty()) {
            throw new RuntimeException("Institutecode is required.");
        }

        visionMissionService.deleteVisionMission(institutecode);
        return ResponseEntity.ok("Vision and Mission deleted successfully.");
    }

    @GetMapping("/getVisionMissionByInstitutecode")
    public ResponseEntity<VisionMission> getVisionMissionByInstitutecode(@RequestParam String institutecode) {
        if (institutecode == null || institutecode.isEmpty()) {
            throw new RuntimeException("Institutecode is required.");
        }

        return visionMissionService.getVisionMissionByInstitutecode(institutecode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
