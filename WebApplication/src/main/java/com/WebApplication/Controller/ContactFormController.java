package com.WebApplication.Controller;

import com.WebApplication.Entity.ContactForm;
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
                                               @RequestParam String maps,
                                               @RequestParam String institutecode,
                                               @RequestParam(required = false) MultipartFile contactImage) {
        try {
            // Check if a ContactForm with the given institutecode already exists
            if (contactFormService.existsByInstitutecode(institutecode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A ContactForm with the given institutecode already exists.");
            }

            // Create a new ContactForm object
            ContactForm contactForm = new ContactForm();
            contactForm.setName(name);
            contactForm.setEmail(email);
            contactForm.setMobileNo(mobileNo);
            contactForm.setCourse(course);
            contactForm.setDescription(description);
            contactForm.setMaps(maps);
            contactForm.setInstitutecode(institutecode);

            // Handle image upload if provided
            if (contactImage != null && !contactImage.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(contactImage); // Upload image to Cloudinary
                contactForm.setContactImage(imageUrl); // Set the image URL to the entity
            }

            // Save and return the created ContactForm
            ContactForm createdContactForm = contactFormService.createContactForm(contactForm, institutecode, contactImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContactForm);
        } catch (IOException e) {
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
                                                              @RequestParam String description,
                                                              @RequestParam String maps,
                                                              @RequestParam(required = false) MultipartFile contactImage) {
        try {
            // Create a temporary ContactForm object to hold the updated data
            ContactForm updatedContactForm = new ContactForm();
            updatedContactForm.setName(name);
            updatedContactForm.setEmail(email);
            updatedContactForm.setMobileNo(mobileNo);
            updatedContactForm.setCourse(course);
            updatedContactForm.setDescription(description);
            updatedContactForm.setMaps(maps);

            // Call the service method to update the ContactForm
            ContactForm result = contactFormService.updateContactFormByInstitutecode(institutecode, updatedContactForm, contactImage);

            // Return the updated ContactForm in the response
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating ContactForm: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteContactForm/{id}")
    public ResponseEntity<String> deleteContactForm(@PathVariable Long id) {
        contactFormService.deleteContactForm(id);
        return ResponseEntity.ok("ContactForm deleted successfully.");
    }

    @GetMapping("/by-contactform-institutecode")
    public Optional<ContactForm> getContactFormByInstitutecode(@RequestParam String institutecode) {
        return contactFormService.getContactFormByInstitutecode(institutecode);
    }

    @GetMapping("/getAllContactForms")
    public ResponseEntity<List<ContactForm>> getAllContactForms() {
        return ResponseEntity.ok(contactFormService.getAllContactForms());
    }
}
