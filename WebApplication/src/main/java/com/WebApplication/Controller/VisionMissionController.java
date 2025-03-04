package com.WebApplication.Controller;

import com.WebApplication.Entity.VisionMission;
import com.WebApplication.Service.VisionMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = {
        "https://pjsofttech.in",
        "https://live.ooacademy.co.in",
        "https://course.yashodapublication.com"
})
public class VisionMissionController {

    @Autowired
    private VisionMissionService visionMissionService;

    @PostMapping("/createVisionMission")
    public ResponseEntity<?> createVisionMission(@RequestParam String vision,
                                                 @RequestParam String mission,
                                                 @RequestParam String visionmissionColor,
                                                 @RequestParam String institutecode,
                                                 @RequestParam(required = false) String directorMessage,
                                                 @RequestParam(required = false) String directorName,
                                                 @RequestParam(required = false) String description,
                                                 @RequestParam(required = false) MultipartFile directorImage) {
        try {
            if (institutecode == null || institutecode.isEmpty()) {
                throw new RuntimeException("Institutecode is required.");
            }

            VisionMission visionMission = new VisionMission();
            visionMission.setVision(vision);
            visionMission.setMission(mission);
            visionMission.setVisionmissionColor(visionmissionColor);
            visionMission.setInstitutecode(institutecode);
            visionMission.setDirectorMessage(directorMessage);
            visionMission.setDirectorName(directorName);
            visionMission.setDescription(description);

            VisionMission createdVisionMission = visionMissionService.saveVisionMission(visionMission, directorImage, institutecode);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVisionMission);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading director image.");
        }
    }

    @PutMapping("/updateVisionMission")
    public ResponseEntity<?> updateVisionMission(@RequestParam String institutecode,
                                                 @RequestParam(required = false) String vision,
                                                 @RequestParam(required = false) String mission,
                                                 @RequestParam(required = false) String visionmissionColor,
                                                 @RequestParam(required = false) String directorMessage,
                                                 @RequestParam(required = false) String directorName,
                                                 @RequestParam(required = false) String description,
                                                 @RequestParam(required = false) MultipartFile directorImage) {
        try {
            VisionMission updatedVisionMission = new VisionMission();
            updatedVisionMission.setVision(vision);
            updatedVisionMission.setMission(mission);
            updatedVisionMission.setVisionmissionColor(visionmissionColor);
            updatedVisionMission.setDirectorMessage(directorMessage);
            updatedVisionMission.setDirectorName(directorName);
            updatedVisionMission.setDescription(description);

            VisionMission result = visionMissionService.updateVisionMission(institutecode, updatedVisionMission, directorImage);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading director image.");
        }
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
