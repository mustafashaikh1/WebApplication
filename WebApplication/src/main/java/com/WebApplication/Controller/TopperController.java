package com.WebApplication.Controller;

import com.WebApplication.Entity.Topper;
import com.WebApplication.Service.TopperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = {
        "https://pjsofttech.in",
        "https://live.ooacademy.co.in",
        "https://course.yashodapublication.com"
})
public class TopperController {

    @Autowired
    private TopperService topperService;

    // ✅ Create Topper (Removed institutecode validation)
    @PostMapping("/createTopper")
    public ResponseEntity<?> createTopper(@RequestParam String name,
                                          @RequestParam Double totalMarks,
                                          @RequestParam String post,
                                          @RequestParam Integer rank,
                                          @RequestParam Integer year,
                                          @RequestParam String topperColor,
                                          @RequestParam String institutecode,
                                          @RequestParam(required = false) List<MultipartFile> topperImages) {
        try {
            Topper topper = new Topper();
            topper.setName(name);
            topper.setTotalMarks(totalMarks);
            topper.setPost(post);
            topper.setRank(rank);
            topper.setYear(year);
            topper.setTopperColor(topperColor);
            topper.setInstitutecode(institutecode);

            Topper createdTopper = topperService.createTopper(topper, institutecode, topperImages);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTopper);
        } catch (IOException e) {
            log.error("Failed to create Topper: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create Topper: " + e.getMessage());
        }
    }

    @PutMapping("/updateTopperById")
    public ResponseEntity<?> updateTopperById(
            @RequestParam Long topperId,
            @RequestParam(required = false) List<MultipartFile> topperImages,
            @RequestParam(required = false) String topperColor,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double totalMarks,
            @RequestParam(required = false) String post,
            @RequestParam(required = false) Integer rank,
            @RequestParam(required = false) Integer year) {
        try {
            Topper updatedTopper = topperService.updateTopperById(
                    topperId, topperImages, topperColor, name, totalMarks, post, rank, year);
            return ResponseEntity.ok(updatedTopper);
        } catch (RuntimeException e) {
            log.error("Topper update failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topper update failed: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error uploading images: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading images: " + e.getMessage());
        }
    }


    // ✅ Delete only Topper data by ImageUrlId and Institutecode (Keep Image in S3)
    @DeleteMapping("/deleteTopperById")
    public ResponseEntity<String> deleteTopperById(@RequestParam Long topperId) {
        try {
            topperService.deleteTopperById(topperId);
            return ResponseEntity.ok("Topper data deleted, but images remain in S3.");
        } catch (RuntimeException e) {
            log.error("Topper deletion failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topper deletion failed: " + e.getMessage());
        }
    }


    // ✅ Delete entire Topper data but keep images
    @DeleteMapping("/deleteTopperByInstitutecode")
    public ResponseEntity<String> deleteTopperByInstitutecode(@RequestParam String institutecode) {
        try {
            topperService.deleteTopperByInstitutecode(institutecode);
            return ResponseEntity.ok("Topper data deleted, but images remain in S3.");
        } catch (RuntimeException e) {
            log.error("Topper deletion failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topper deletion failed: " + e.getMessage());
        }
    }


    // ✅ Get Topper by ID
    @GetMapping("/getTopperById")
    public ResponseEntity<?> getTopperById(@RequestParam Long topperId) {
        Optional<Topper> topper = topperService.getTopperById(topperId);

        if (topper.isPresent()) {
            return ResponseEntity.ok(topper.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topper not found with ID: " + topperId);
        }
    }


    @GetMapping("/getAllToppers")
    public ResponseEntity<List<Topper>> getAllToppersByInstitutecode(@RequestParam String institutecode) {
        List<Topper> toppers = topperService.getAllToppersByInstitutecode(institutecode);

        if (toppers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        return ResponseEntity.ok(toppers);
    }


}
