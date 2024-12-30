package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.ContactForm;
import com.WebApplication.Repository.ContactFormRepository;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.ContactFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ContactFormServiceImpl implements ContactFormService {

    @Autowired
    private ContactFormRepository contactFormRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public ContactForm createContactForm(ContactForm contactForm, String institutecode) {
        // Remove the check for an existing ContactForm with the same institutecode
        contactForm.setInstitutecode(institutecode);
        return contactFormRepository.save(contactForm);
    }

    @Override
    public ContactForm updateContactFormByInstitutecode(String institutecode, ContactForm updatedContactForm) {
        // Fetch the existing ContactForm by institutecode
        ContactForm existingContactForm = contactFormRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("ContactForm not found with institutecode: " + institutecode));

        // Update fields
        existingContactForm.setName(updatedContactForm.getName());
        existingContactForm.setMobileNo(updatedContactForm.getMobileNo());
        existingContactForm.setCourse(updatedContactForm.getCourse());
        existingContactForm.setDescription(updatedContactForm.getDescription());
        existingContactForm.setEmail(updatedContactForm.getEmail());

        // Save and return the updated ContactForm
        return contactFormRepository.save(existingContactForm);
    }
    @Override
    public boolean existsByInstitutecode(String institutecode) {
        // Check if a ContactForm exists with the given institutecode
        return contactFormRepository.existsByInstitutecode(institutecode);
    }

    @Override
    public void deleteContactForm(String institutecode) {
        // Attempt to find the ContactForm by institutecode
        ContactForm contactForm = contactFormRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("No record found with the given institutecode: " + institutecode));

        // Delete the found ContactForm
        contactFormRepository.delete(contactForm);
    }



    @Override
    public List<ContactForm> getAllContactForms() {
        // Fetch all ContactForms
        return contactFormRepository.findAll();
    }







    @Override
    public Optional<ContactForm> getContactFormByInstitutecode(String institutecode) {
        // Fetch the ContactForm by institutecode
        return contactFormRepository.findByInstitutecode(institutecode);
    }


             //FOR MAP AND IMAGES//



    @Override
    public ContactForm createContactImageAndMap(String institutecode, String maps, MultipartFile contactImage) throws IOException {
        // Check if the record already exists
        if (contactFormRepository.existsByInstitutecode(institutecode)) {
            ContactForm existingContactForm = contactFormRepository.findByInstitutecode(institutecode).orElseThrow();
            if (existingContactForm.getMaps() != null || existingContactForm.getContactImage() != null) {
                throw new RuntimeException("Maps and ContactImage already exist for this institutecode.");
            }
        }

        // Handle image upload
        String imageUrl = null;
        if (contactImage != null && !contactImage.isEmpty()) {
            imageUrl = cloudinaryService.uploadImage(contactImage);
        }

        // Create or update the record
        ContactForm contactForm = contactFormRepository.findByInstitutecode(institutecode)
                .orElse(new ContactForm());
        contactForm.setInstitutecode(institutecode);
        contactForm.setMaps(maps);
        contactForm.setContactImage(imageUrl);

        return contactFormRepository.save(contactForm);
    }

    @Override
    public Optional<ContactForm> getContactImageAndMapByInstitutecode(String institutecode) {
        return contactFormRepository.findByInstitutecode(institutecode);
    }

    @Override
    public ContactForm updateContactImageAndMap(String institutecode, String maps, MultipartFile contactImage) throws IOException {
        ContactForm contactForm = contactFormRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("No record found with the given institutecode."));

        if (maps != null) {
            contactForm.setMaps(maps);
        }

        if (contactImage != null && !contactImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(contactImage);
            contactForm.setContactImage(imageUrl);
        }

        return contactFormRepository.save(contactForm);
    }

    @Override
    public void deleteContactImageAndMap(String institutecode) {
        ContactForm contactForm = contactFormRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("No record found with the given institutecode."));
        contactForm.setMaps(null);
        contactForm.setContactImage(null);
        contactFormRepository.save(contactForm);
    }
}