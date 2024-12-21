package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.ContactForm;
import com.WebApplication.Repository.ContactFormRepository;
import com.WebApplication.Service.ContactFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactFormServiceImpl implements ContactFormService {

    @Autowired
    private ContactFormRepository contactFormRepository;



    @Override
    public ContactForm createContactForm(ContactForm contactForm, String institutecode) {
        contactForm.setInstitutecode(institutecode); // Set institutecode in the entity
        return contactFormRepository.save(contactForm);
    }

    @Override
    public ContactForm updateContactForm(Long id, ContactForm contactForm) {
        Optional<ContactForm> existingContactForm = contactFormRepository.findById(id);
        if (existingContactForm.isPresent()) {
            ContactForm updatedForm = existingContactForm.get();
            updatedForm.setName(contactForm.getName());
            updatedForm.setMobileNo(contactForm.getMobileNo());
            updatedForm.setCourse(contactForm.getCourse());
            updatedForm.setDescription(contactForm.getDescription());
            return contactFormRepository.save(updatedForm);
        } else {
            throw new RuntimeException("ContactForm not found with id: " + id);
        }
    }

    @Override
    public void deleteContactForm(Long id) {
        contactFormRepository.deleteById(id);
    }

    @Override
    public ContactForm getContactFormById(Long id) {
        return contactFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContactForm not found with id: " + id));
    }

    @Override
    public List<ContactForm> getAllContactForms(String institutecode) {
        return contactFormRepository.findByInstitutecode(institutecode); // Fetch filtered by institutecode
    }
}
