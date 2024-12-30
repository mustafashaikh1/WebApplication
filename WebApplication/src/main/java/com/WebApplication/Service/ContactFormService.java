package com.WebApplication.Service;

import com.WebApplication.Entity.ContactForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ContactFormService {
    ContactForm createContactForm(ContactForm contactForm, String institutecode);

    boolean existsByInstitutecode(String institutecode);

    void deleteContactForm(Long id);

    List<ContactForm> getAllContactForms();

    ContactForm updateContactFormByInstitutecode(String institutecode, ContactForm updatedContactForm);



    Optional<ContactForm> getContactFormByInstitutecode(String institutecode);

    ContactForm createContactImageAndMap(String institutecode, String maps, MultipartFile contactImage) throws IOException;

    Optional<ContactForm> getContactImageAndMapByInstitutecode(String institutecode);

    ContactForm updateContactImageAndMap(String institutecode, String maps, MultipartFile contactImage) throws IOException;

    void deleteContactImageAndMap(String institutecode);
}

