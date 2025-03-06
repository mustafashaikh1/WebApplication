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

    // ✅ Update Topper by ImageUrlId and Institutecode (Without Deleting Previous Images)
    @PutMapping("/updateTopperByImageUrlIdAndInstitutecode")
    public ResponseEntity<?> updateTopperByImageUrlIdAndInstitutecode(
            @RequestParam Long imageUrlId,
            @RequestParam String institutecode,
            @RequestParam(required = false) List<MultipartFile> topperImages,
            @RequestParam(required = false) String topperColor) {
        try {
            Topper updatedTopper = topperService.updateTopperByImageUrlIdAndInstitutecode(imageUrlId, institutecode, topperImages, topperColor);
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
    @DeleteMapping("/deleteTopper")
    public ResponseEntity<String> deleteTopperByImageUrlIdAndInstitutecode(
            @RequestParam Long imageUrlId,
            @RequestParam String institutecode) {
        try {
            topperService.deleteTopperByImageUrlIdAndInstitutecode(imageUrlId, institutecode);
            return ResponseEntity.ok("Image URL ID removed from database, but image remains in S3.");
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

    // ✅ Get all Toppers by Institutecode
    @GetMapping("/getAllToppers")
    public ResponseEntity<Optional<Topper>> getAllToppersByInstitutecode(@RequestParam String institutecode) {
        Optional<Topper> toppers = topperService.getAllToppersByInstitutecode(institutecode);
        return ResponseEntity.ok(toppers);
    }
}
