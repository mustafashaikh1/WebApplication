package com.WebApplication.Controller;

import com.WebApplication.Entity.ContactForm;
import com.WebApplication.Service.ContactFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {
        "https://pjsofttech.in",
        "https://live.ooacademy.co.in",
        "https://course.yashodapublication.com"
})
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @PostMapping("/createContactForm")
    public ResponseEntity<?> createContactForm(
            @RequestParam String name,
            @RequestParam String mobileNo,
            @RequestParam String course,
            @RequestParam String description,
            @RequestParam String email,
            @RequestParam String academicYear,
            @RequestParam String institutecode) {

        try {
            if (institutecode == null || institutecode.trim().isEmpty()) {
                throw new IllegalArgumentException("Institutecode is required and cannot be empty.");
            }

            ContactForm contactForm = new ContactForm();
            contactForm.setName(name);
            contactForm.setMobileNo(mobileNo);
            contactForm.setCourse(course);
            contactForm.setDescription(description);
            contactForm.setEmail(email);
            contactForm.setAcademicYear(academicYear);
            contactForm.setInstitutecode(institutecode);

            ContactForm savedContactForm = contactFormService.saveContactForm(contactForm);
            return ResponseEntity.ok(savedContactForm);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("updateContactForm/{id}")
    public ResponseEntity<?> updateContactForm(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String mobileNo,
            @RequestParam String course,
            @RequestParam String description,
            @RequestParam String email,
            @RequestParam String academicYear,
            @RequestParam String institutecode) {

        try {
            if (institutecode == null || institutecode.trim().isEmpty()) {
                throw new IllegalArgumentException("Institutecode is required and cannot be empty.");
            }

            ContactForm contactForm = new ContactForm();
            contactForm.setName(name);
            contactForm.setMobileNo(mobileNo);
            contactForm.setCourse(course);
            contactForm.setDescription(description);
            contactForm.setEmail(email);
            contactForm.setAcademicYear(academicYear);
            contactForm.setInstitutecode(institutecode);

            ContactForm updatedContactForm = contactFormService.updateContactForm(id, contactForm);
            return ResponseEntity.ok(updatedContactForm);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/getContactFormById/{id}")
    public ResponseEntity<ContactForm> getContactFormById(@PathVariable Long id) {
        ContactForm contactForm = contactFormService.getContactFormById(id);
        return ResponseEntity.ok(contactForm);
    }

    @GetMapping("/getAllContactForms")
    public ResponseEntity<List<ContactForm>> getAllContactForms() {
        List<ContactForm> contactForms = contactFormService.getAllContactForms();
        return ResponseEntity.ok(contactForms);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContactForm(@PathVariable Long id) {
        contactFormService.deleteContactForm(id);
        return ResponseEntity.ok("ContactForm deleted successfully with ID: " + id);
    }
}
