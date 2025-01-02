package com.WebApplication.Controller;

import com.WebApplication.Entity.ContactForm;
import com.WebApplication.Service.ContactFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @PostMapping("/createContactForm")
    public ResponseEntity<ContactForm> createContactForm(
            @RequestParam String name,
            @RequestParam String mobileNo,
            @RequestParam String course,
            @RequestParam String description,
            @RequestParam String email,
            @RequestParam String institutecode) {

        ContactForm contactForm = new ContactForm();
        contactForm.setName(name);
        contactForm.setMobileNo(mobileNo);
        contactForm.setCourse(course);
        contactForm.setDescription(description);
        contactForm.setEmail(email);
        contactForm.setInstitutecode(institutecode);

        ContactForm savedContactForm = contactFormService.saveContactForm(contactForm);
        return ResponseEntity.ok(savedContactForm);
    }

    @PutMapping("updateContactForm/{id}")
    public ResponseEntity<ContactForm> updateContactForm(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String mobileNo,
            @RequestParam String course,
            @RequestParam String description,
            @RequestParam String email,
            @RequestParam String institutecode) {

        ContactForm contactForm = new ContactForm();
        contactForm.setName(name);
        contactForm.setMobileNo(mobileNo);
        contactForm.setCourse(course);
        contactForm.setDescription(description);
        contactForm.setEmail(email);
        contactForm.setInstitutecode(institutecode);

        ContactForm updatedContactForm = contactFormService.updateContactForm(id, contactForm);
        return ResponseEntity.ok(updatedContactForm);
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
