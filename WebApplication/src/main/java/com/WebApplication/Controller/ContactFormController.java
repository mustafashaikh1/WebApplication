package com.WebApplication.Controller;

import com.WebApplication.Entity.ContactForm;
import com.WebApplication.Service.ContactFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Slf4j
@RestController
//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "https://pjsofttech.in")
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @PostMapping("/createContactForm")
    public ResponseEntity<ContactForm> createContactForm(
            @RequestBody ContactForm contactForm,
            @RequestParam String institutecode) {
        ContactForm createdForm = contactFormService.createContactForm(contactForm, institutecode);
        return ResponseEntity.ok(createdForm);
    }

    @PutMapping("/updateContactForm/{id}")
    public ResponseEntity<ContactForm> updateContactForm(
            @PathVariable Long id,
            @RequestBody ContactForm contactForm) {
        ContactForm updatedForm = contactFormService.updateContactForm(id, contactForm);
        return ResponseEntity.ok(updatedForm);
    }

    @DeleteMapping("/deleteContactForm/{id}")
    public ResponseEntity<String> deleteContactForm(@PathVariable Long id) {
        contactFormService.deleteContactForm(id);
        return ResponseEntity.ok("ContactForm deleted successfully.");
    }

    @GetMapping("/getContactFormById/{id}")

    public ResponseEntity<ContactForm> getContactFormById(@PathVariable Long id) {
        ContactForm contactForm = contactFormService.getContactFormById(id);
        return ResponseEntity.ok(contactForm);
    }

    @GetMapping("/getAllContactForms")
    public ResponseEntity<List<ContactForm>> getAllContactForms(
            @RequestParam String institutecode) {
        List<ContactForm> contactForms = contactFormService.getAllContactForms(institutecode);
        return ResponseEntity.ok(contactForms);
    }
}
