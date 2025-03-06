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

    // ✅ Create Topper
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

    // ✅ Update Topper by topperId and Institutecode
    @PutMapping("/updateTopperByTopperIdAndInstitutecode")
    public ResponseEntity<?> updateTopperByTopperIdAndInstitutecode(
            @RequestParam Long topperId,
            @RequestParam String institutecode,
            @RequestParam(required = false) List<MultipartFile> topperImages,
            @RequestParam(required = false) String topperColor) {
        try {
            Topper updatedTopper = topperService.updateTopperByTopperIdAndInstitutecode(topperId, institutecode, topperImages, topperColor);
            return ResponseEntity.ok(updatedTopper);
        } catch (RuntimeException e) {
            log.error("Topper update failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topper update failed: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error uploading images: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading images: " + e.getMessage());
        }
    }

    // ✅ Delete Topper by topperId and Institutecode
    @DeleteMapping("/deleteTopper")
    public ResponseEntity<String> deleteTopperByTopperIdAndInstitutecode(@RequestParam Long topperId, @RequestParam String institutecode) {
        try {
            topperService.deleteTopperByTopperIdAndInstitutecode(topperId, institutecode);
            return ResponseEntity.ok("Topper deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topper deletion failed: " + e.getMessage());
        }
    }

    // ✅ Get all Toppers by Institutecode
    @GetMapping("/getAllToppers")
    public ResponseEntity<Optional<Topper>> getAllToppersByInstitutecode(@RequestParam String institutecode) {
        Optional<Topper> toppers = topperService.getAllToppersByInstitutecode(institutecode);
        return ResponseEntity.ok(toppers);
    }
}
