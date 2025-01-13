package com.WebApplication.Controller;

import com.WebApplication.Entity.JobCareerOption;
import com.WebApplication.Service.JobCareerOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class JobCareerOptionContoller {


    @Autowired
    private JobCareerOptionService jobCareerOptionService;

    @PostMapping("/createJobCareerOption")
    public ResponseEntity<JobCareerOption> createJobCareerOption(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String location,
            @RequestParam String salaryRange,
            @RequestParam String responsibilities,
            @RequestParam String JobCareerOptionColor,
            @RequestParam String institutecode) {

        JobCareerOption jobCareerOption = new JobCareerOption();
        jobCareerOption.setTitle(title);
        jobCareerOption.setDescription(description);
        jobCareerOption.setLocation(location);
        jobCareerOption.setSalaryRange(salaryRange);
        jobCareerOption.setResponsibilities(responsibilities);
        jobCareerOption.setInstitutecode(institutecode);
        jobCareerOption.setJobCareerOptionColor(JobCareerOptionColor);
        jobCareerOption.setPostDate(LocalDate.now());  // Assuming you want to set the post date to today's date

        JobCareerOption createdJobCareerOption = jobCareerOptionService.createJobCareerOption(jobCareerOption, institutecode);
        return ResponseEntity.ok(createdJobCareerOption);
    }


    @PutMapping("/updateJobCareerOption/{id}")
    public ResponseEntity<JobCareerOption> updateJobCareerOption(
            @PathVariable long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String location,
            @RequestParam String salaryRange,
            @RequestParam String JobCareerOptionColor,
            @RequestParam String responsibilities) {

        JobCareerOption jobCareerOption = new JobCareerOption();
        jobCareerOption.setTitle(title);
        jobCareerOption.setDescription(description);
        jobCareerOption.setLocation(location);
        jobCareerOption.setSalaryRange(salaryRange);
        jobCareerOption.setResponsibilities(responsibilities);
        jobCareerOption.setJobCareerOptionColor(JobCareerOptionColor);

        JobCareerOption updatedJobCareerOption = jobCareerOptionService.updateJobCareerOption(id, jobCareerOption);
        return ResponseEntity.ok(updatedJobCareerOption);
    }


    @PutMapping("/updateByInstitutecode")
    public ResponseEntity<?> updateJobCareerOptionByInstitutecode(
            @RequestParam String institutecode,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String salaryRange,
            @RequestParam String JobCareerOptionColor,
            @RequestParam(required = false) String responsibilities) {
        try {
            // Create a JobCareerOption object for the update
            JobCareerOption updatedJobCareerOption = new JobCareerOption();

            // Set fields if provided
            if (title != null) updatedJobCareerOption.setTitle(title);
            if (description != null) updatedJobCareerOption.setDescription(description);
            if (location != null) updatedJobCareerOption.setLocation(location);
            if (salaryRange != null) updatedJobCareerOption.setSalaryRange(salaryRange);
            if (responsibilities != null) updatedJobCareerOption.setResponsibilities(responsibilities);
            if (responsibilities != null) updatedJobCareerOption.setJobCareerOptionColor(JobCareerOptionColor);

            // Call the service to update the JobCareerOption
            JobCareerOption result = jobCareerOptionService.updateJobCareerOptionByInstitutecode(
                    institutecode, updatedJobCareerOption);

            // Return the updated JobCareerOption
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getAllJobCareerOptions")
    public ResponseEntity<List<JobCareerOption>> getAllJobCareerOptions(
            @RequestParam(required = false) String institutecode) {

        List<JobCareerOption> jobCareerOptions;

        if (institutecode != null && !institutecode.isEmpty()) {
            jobCareerOptions = jobCareerOptionService.getJobCareerOptionsByInstitutecode(institutecode)
                    .orElseThrow(() -> new RuntimeException("No JobCareerOption found for institutecode: " + institutecode));
        } else {
            jobCareerOptions = jobCareerOptionService.getAllJobCareerOptions();
        }

        return ResponseEntity.ok(jobCareerOptions);
    }
    @GetMapping("/getJobCareerOptionsByInstitutecode")
    public ResponseEntity<List<JobCareerOption>> getJobCareerOptionsByInstitutecode(@RequestParam String institutecode) {
        return jobCareerOptionService.getJobCareerOptionsByInstitutecode(institutecode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/deleteJobCareerOption/{id}")
    public ResponseEntity<String> deleteJobCareerOption(@PathVariable long id) {
        jobCareerOptionService.deleteJobCareerOption(id);
        return ResponseEntity.ok("JobCareerOption deleted successfully.");
    }



}
