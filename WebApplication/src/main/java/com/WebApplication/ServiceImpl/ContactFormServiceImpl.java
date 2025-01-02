package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.ContactForm;
import com.WebApplication.Repository.ContactFormRepository;
import com.WebApplication.Service.ContactFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactFormServiceImpl implements ContactFormService {

    @Autowired
    private ContactFormRepository contactFormRepository;

    @Override
    public ContactForm saveContactForm(ContactForm contactForm) {
        return contactFormRepository.save(contactForm);
    }

    @Override
    public ContactForm updateContactForm(Long id, ContactForm contactForm) {
        ContactForm existingContactForm = contactFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContactForm not found with ID: " + id));
        existingContactForm.setName(contactForm.getName());
        existingContactForm.setMobileNo(contactForm.getMobileNo());
        existingContactForm.setCourse(contactForm.getCourse());
        existingContactForm.setDescription(contactForm.getDescription());
        existingContactForm.setEmail(contactForm.getEmail());
        existingContactForm.setInstitutecode(contactForm.getInstitutecode());
        return contactFormRepository.save(existingContactForm);
    }

    @Override
    public ContactForm getContactFormById(Long id) {
        return contactFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContactForm not found with ID: " + id));
    }

    @Override
    public List<ContactForm> getAllContactForms() {
        return contactFormRepository.findAll();
    }

    @Override
    public void deleteContactForm(Long id) {
        if (!contactFormRepository.existsById(id)) {
            throw new RuntimeException("ContactForm not found with ID: " + id);
        }
        contactFormRepository.deleteById(id);
    }
}
