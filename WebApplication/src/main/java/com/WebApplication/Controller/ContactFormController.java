package com.WebApplication.Controller;

import com.WebApplication.Entity.ContactForm;
import com.WebApplication.Entity.Facility;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.ContactFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/createContactForm")
    public ResponseEntity<?> createContactForm(@RequestParam String name,
                                               @RequestParam String email,
                                               @RequestParam String mobileNo,
                                               @RequestParam String course,
                                               @RequestParam String description,
                                               @RequestParam String institutecode) {
        try {
            // Create a new ContactForm object
            ContactForm contactForm = new ContactForm();
            contactForm.setName(name);
            contactForm.setEmail(email);
            contactForm.setMobileNo(mobileNo);
            contactForm.setCourse(course);
            contactForm.setDescription(description);
            contactForm.setInstitutecode(institutecode);

            // Save and return the created ContactForm
            ContactForm createdContactForm = contactFormService.createContactForm(contactForm, institutecode);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContactForm);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create ContactForm: " + e.getMessage());
        }
    }

    @PutMapping("/updateContactForm")
    public ResponseEntity<?> updateContactFormByInstitutecode(@RequestParam String institutecode,
                                                              @RequestParam String name,
                                                              @RequestParam String email,
                                                              @RequestParam String mobileNo,
                                                              @RequestParam String course,
                                                              @RequestParam String description) {
        try {
            // Create a temporary ContactForm object to hold the updated data
            ContactForm updatedContactForm = new ContactForm();
            updatedContactForm.setName(name);
            updatedContactForm.setEmail(email);
            updatedContactForm.setMobileNo(mobileNo);
            updatedContactForm.setCourse(course);
            updatedContactForm.setDescription(description);

            // Call the service method to update the ContactForm
            ContactForm result = contactFormService.updateContactFormByInstitutecode(institutecode, updatedContactForm);

            // Return the updated ContactForm in the response
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating ContactForm: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteContactForm")
    public ResponseEntity<String> deleteContactForm(@RequestParam String institutecode) {
        try {
            contactFormService.deleteContactForm(institutecode);
            return ResponseEntity.ok("ContactForm deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete ContactForm: " + e.getMessage());
        }
    }







    @GetMapping("/getContactFormByInstitutecode")
    public ResponseEntity<ContactForm> getContactFormByInstitutecode(@RequestParam String institutecode) {
        return contactFormService.getContactFormByInstitutecode(institutecode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }



    @GetMapping("/getAllContactForms")
    public ResponseEntity<List<ContactForm>> getAllContactForms() {
        try {
            List<ContactForm> contactForms = contactFormService.getAllContactForms();
            return ResponseEntity.ok(contactForms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }







    //FOR MAP AND IMAGES//


    @PostMapping("/createImageMap")
    public ResponseEntity<?> createContactImageAndMap(@RequestParam String institutecode,
                                                      @RequestParam String maps,
                                                      @RequestParam(required = false) MultipartFile contactImage) {
        try {
            ContactForm createdContactForm = contactFormService.createContactImageAndMap(institutecode, maps, contactImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContactForm);
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/getImageMap")
    public ResponseEntity<ContactForm> getFacilityByInstitutecode(@RequestParam String institutecode) {
        return contactFormService.getContactFormByInstitutecode(institutecode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }






    @PutMapping("/updateImageMap")
    public ResponseEntity<?> updateContactImageAndMap(@RequestParam String institutecode,
                                                      @RequestParam(required = false) String maps,
                                                      @RequestParam(required = false) MultipartFile contactImage) {
        try {
            // Update only the maps and contactImage fields without changing institutecode
            ContactForm updatedContactForm = contactFormService.updateContactImageAndMap(institutecode, maps, contactImage);
            return ResponseEntity.ok(updatedContactForm);
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }


    @DeleteMapping("/deleteImageMap")
    public ResponseEntity<?> deleteContactImageAndMap(@RequestParam String institutecode) {
        try {
            contactFormService.deleteContactImageAndMap(institutecode);
            return ResponseEntity.ok("Maps and ContactImage removed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}