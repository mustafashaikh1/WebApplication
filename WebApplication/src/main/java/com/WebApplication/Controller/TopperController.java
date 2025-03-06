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




    @DeleteMapping("/deleteTopper/{id}")
    public ResponseEntity<String> deleteTopper(@PathVariable Long id) {
        if (id == null) {
            throw new RuntimeException("ID is required for deleting a topper.");
        }
        topperService.deleteTopper(id);
        return ResponseEntity.ok("Topper deleted successfully.");
    }

    @GetMapping("/getTopperById/{id}")
    public ResponseEntity<Topper> getTopperById(@PathVariable Long id) {
        if (id == null) {
            throw new RuntimeException("ID is required to fetch the topper.");
        }
        return ResponseEntity.ok(topperService.getTopperById(id));
    }

    @GetMapping("/getAllToppers")
    public ResponseEntity<List<Topper>> getAllToppers(@RequestParam String institutecode) {
        if (institutecode == null || institutecode.isEmpty()) {
            throw new RuntimeException("Institutecode is required to fetch toppers.");
        }
        return ResponseEntity.ok(topperService.getAllToppers(institutecode));
    }
}
