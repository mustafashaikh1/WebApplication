package com.WebApplication.Controller;

import com.WebApplication.DTO.WebHRDetailsDTO;
import com.WebApplication.Entity.JobCareerOption;
import com.WebApplication.Entity.WebHRDetails;
import com.WebApplication.Repository.JobCareerOptionRepository;
import com.WebApplication.Service.JobCareerOptionService;
import com.WebApplication.Service.WebHRDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
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

    @Autowired
    private JobCareerOptionRepository jobCareerOptionRepository;

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
    @GetMapping("/getAllWebHRDetails")
    public ResponseEntity<?> getAllWebHRDetails() {
        return ResponseEntity.ok(webHRDetailsService.getAllWebHRDetails());
    }

    // Get WebHRDetails by ID
    @GetMapping("/getWebHRDetailsById/{id}")
    public ResponseEntity<?> getWebHRDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(webHRDetailsService.getWebHRDetailsById(id));
    }

    // Delete WebHRDetails
    @DeleteMapping("/deleteWebHRDetails/{id}")
    public ResponseEntity<?> deleteWebHRDetails(@PathVariable Long id) {
        webHRDetailsService.deleteWebHRDetails(id);
        return ResponseEntity.ok("WebHRDetails deleted successfully.");
    }

    @GetMapping("/getHrDetailsByJobCareerOption/{jobId}")
    public ResponseEntity<WebHRDetailsDTO> getHrDetailsByJobCareerOption(@PathVariable("jobId") Long jobId) {
        Optional<JobCareerOption> jobOptional = jobCareerOptionRepository.findById(jobId);

        if (jobOptional.isPresent()) {
            WebHRDetails hrDetails = jobOptional.get().getWebHRDetails();
            if (hrDetails != null) {
                // Convert Entity to DTO
                WebHRDetailsDTO hrDetailsDTO = new WebHRDetailsDTO(
                        hrDetails.getId(),
                        hrDetails.getHrName(),
                        hrDetails.getEmail(),
                        hrDetails.getContact(),
                        hrDetails.getJobCareerOption().getId() // Fetch only the JobCareerOption ID
                );
                return ResponseEntity.ok(hrDetailsDTO);
            }
            return ResponseEntity.notFound().build(); // No HR details found for the job
        }
        return ResponseEntity.notFound().build(); // JobCareerOption not found
    }

}
