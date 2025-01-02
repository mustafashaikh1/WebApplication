package com.WebApplication.Service;

import com.WebApplication.Entity.ContactForm;

import java.util.List;

public interface ContactFormService {

    ContactForm saveContactForm(ContactForm contactForm);

    ContactForm updateContactForm(Long id, ContactForm contactForm);

    ContactForm getContactFormById(Long id);

    List<ContactForm> getAllContactForms();

    void deleteContactForm(Long id);
}
