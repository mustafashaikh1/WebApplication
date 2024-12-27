package com.WebApplication.Service;

import com.WebApplication.Entity.ContactForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ContactFormService {
    ContactForm createContactForm(ContactForm contactForm, String institutecode, MultipartFile contactImage) throws IOException;


    boolean existsByInstitutecode(String institutecode);

    void deleteContactForm(Long id);

    List<ContactForm> getAllContactForms();

    ContactForm updateContactFormByInstitutecode(String institutecode, ContactForm updatedContactForm, MultipartFile contactImage) throws IOException;


    Optional<ContactForm> getContactFormByInstitutecode(String institutecode);





}
