package com.WebApplication.Controller;

import com.WebApplication.Entity.JobCareerOption;
import com.WebApplication.Entity.WebHRDetails;
import com.WebApplication.Service.JobCareerOptionService;
import com.WebApplication.Service.WebHRDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/webhrdetails")
@CrossOrigin(origins = {
        "https://pjsofttech.in",
        "https://live.ooacademy.co.in",
        "https://course.yashodapublication.com",
        "https://lokrajyaacademy.com"
})
public class WebHRDetailsController {

    @Autowired
    private WebHRDetailsService webHRDetailsService;

    @Autowired
    private JobCareerOptionService jobCareerOptionService;

    // Create WebHRDetails with jobCareerOptionId in RequestParam
    @PostMapping("/createWebHRDetails")
    public ResponseEntity<WebHRDetails> createWebHRDetails(
            @RequestParam Long jobCareerOptionId,
            @RequestBody WebHRDetails webHRDetails) {

        JobCareerOption jobCareerOption = jobCareerOptionService.getJobCareerOptionById(jobCareerOptionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "JobCareerOption not found"));

        webHRDetails.setJobCareerOption(jobCareerOption);
        WebHRDetails savedDetails = webHRDetailsService.createWebHRDetails(webHRDetails);
        return ResponseEntity.ok(savedDetails);
    }

    // Update WebHRDetails with jobCareerOptionId in RequestParam
    @PutMapping("/updateWebHRDetails/{id}")
    public ResponseEntity<WebHRDetails> updateWebHRDetails(
            @PathVariable Long id,
            @RequestParam Long jobCareerOptionId,
            @RequestBody WebHRDetails updatedDetails) {

        JobCareerOption jobCareerOption = jobCareerOptionService.getJobCareerOptionById(jobCareerOptionId)
                .orElseThrow(() -> new RuntimeException("JobCareerOption not found"));

        updatedDetails.setJobCareerOption(jobCareerOption);
        WebHRDetails result = webHRDetailsService.updateWebHRDetails(id, updatedDetails);
        return ResponseEntity.ok(result);
    }

    // Get all WebHRDetails
    @GetMapping
    public ResponseEntity<?> getAllWebHRDetails() {
        return ResponseEntity.ok(webHRDetailsService.getAllWebHRDetails());
    }

    // Get WebHRDetails by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getWebHRDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(webHRDetailsService.getWebHRDetailsById(id));
    }

    // Delete WebHRDetails
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWebHRDetails(@PathVariable Long id) {
        webHRDetailsService.deleteWebHRDetails(id);
        return ResponseEntity.ok("WebHRDetails deleted successfully.");
    }
}
