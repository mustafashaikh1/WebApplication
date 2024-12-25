package com.WebApplication.Controller;

import com.WebApplication.Entity.Topper;
import com.WebApplication.Service.TopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "https://pjsofttech.in")
public class TopperController {

    @Autowired
    private TopperService topperService;


    @PostMapping("/createTopper")
    public ResponseEntity<Topper> createTopper(
            @RequestParam String institutecode, // Request param for institutecode
            @RequestParam String name, // Individual fields from request part
            @RequestParam Double totalMarks,
            @RequestParam String post,
            @RequestParam Integer rank,
            @RequestParam  Integer year,
            @RequestParam(value = "topperImage", required = false) MultipartFile topperImage) throws IOException {


        Topper topper = new Topper();
        topper.setName(name);
        topper.setTotalMarks(totalMarks);
        topper.setPost(post);
        topper.setRank(rank);
        topper.setYear(year);

        return ResponseEntity.ok(topperService.createTopper(topper, institutecode, topperImage));
    }


    @PutMapping("/updateTopper/{id}")
    public ResponseEntity<Topper> updateTopper(
            @PathVariable Long id,

            @RequestParam String name, // Individual fields from request part
            @RequestParam Double totalMarks,
            @RequestParam String post,
            @RequestParam Integer rank,
            @RequestParam  Integer year,
            @RequestParam(value = "topperImage", required = false) MultipartFile topperImage) throws IOException {


        Topper topper = new Topper();
        topper.setName(name);
        topper.setTotalMarks(totalMarks);
        topper.setPost(post);
        topper.setRank(rank);
        topper.setYear(year);


        return ResponseEntity.ok(topperService.updateTopper(id, topper, topperImage));
    }

    @DeleteMapping("/deleteTopper/{id}")
    public ResponseEntity<String> deleteTopper(@PathVariable Long id) {
        topperService.deleteTopper(id);
        return ResponseEntity.ok("Topper deleted successfully.");
    }

    @GetMapping("/getTopperById/{id}")
    public ResponseEntity<Topper> getTopperById(@PathVariable Long id) {
        return ResponseEntity.ok(topperService.getTopperById(id));
    }

    @GetMapping("/getAllToppers")
    public ResponseEntity<List<Topper>> getAllToppers(@RequestParam String institutecode) {
        return ResponseEntity.ok(topperService.getAllToppers(institutecode));
    }
}
