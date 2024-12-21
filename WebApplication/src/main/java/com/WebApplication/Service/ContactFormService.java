package com.WebApplication.Service;

import com.WebApplication.Entity.ContactForm;
import java.util.List;

public interface ContactFormService {
    ContactForm createContactForm(ContactForm contactForm,String institutecode);
    ContactForm updateContactForm(Long id, ContactForm contactForm);
    void deleteContactForm(Long id);
    ContactForm getContactFormById(Long id);
    List<ContactForm> getAllContactForms(String institutecode);
}
