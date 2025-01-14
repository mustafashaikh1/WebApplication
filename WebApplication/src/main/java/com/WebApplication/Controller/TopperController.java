package com.WebApplication.Controller;

import com.WebApplication.Entity.Topper;
import com.WebApplication.Service.TopperService;
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
public class TopperController {

    @Autowired
    private TopperService topperService;

    @PostMapping("/createTopper")
    public ResponseEntity<Topper> createTopper(
            @RequestParam String institutecode,
            @RequestParam String name,
            @RequestParam Double totalMarks,
            @RequestParam String post,
            @RequestParam Integer rank,
            @RequestParam Integer year,
            @RequestParam String topperColor,
            @RequestParam(value = "topperImage", required = false) MultipartFile topperImage) throws IOException {

        if (institutecode == null || institutecode.isEmpty()) {
            throw new RuntimeException("Institutecode is required.");
        }

        Topper topper = new Topper();
        topper.setName(name);
        topper.setTotalMarks(totalMarks);
        topper.setPost(post);
        topper.setRank(rank);
        topper.setYear(year);
        topper.setTopperColor(topperColor);

        return ResponseEntity.ok(topperService.createTopper(topper, institutecode, topperImage));
    }

    @PutMapping("/updateTopper/{id}")
    public ResponseEntity<Topper> updateTopper(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam Double totalMarks,
            @RequestParam String post,
            @RequestParam Integer rank,
            @RequestParam Integer year,
            @RequestParam String topperColor,
            @RequestParam(value = "topperImage", required = false) MultipartFile topperImage) throws IOException {

        if (id == null) {
            throw new RuntimeException("ID is required for updating a topper.");
        }

        Topper topper = new Topper();
        topper.setName(name);
        topper.setTotalMarks(totalMarks);
        topper.setPost(post);
        topper.setRank(rank);
        topper.setYear(year);
        topper.setTopperColor(topperColor);

        return ResponseEntity.ok(topperService.updateTopper(id, topper, topperImage));
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
