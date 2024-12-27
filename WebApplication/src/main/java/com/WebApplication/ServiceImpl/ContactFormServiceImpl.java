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
    public ContactForm createContactForm(ContactForm contactForm, String institutecode, MultipartFile contactImage) throws IOException {
        // Check if a ContactForm already exists for the given institutecode
        if (contactFormRepository.existsByInstitutecode(institutecode)) {
            throw new RuntimeException("A ContactForm with institutecode '" + institutecode + "' already exists.");
        }

        // Handle image upload
        if (contactImage != null && !contactImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(contactImage);
            contactForm.setContactImage(imageUrl);
        }

        contactForm.setInstitutecode(institutecode);
        return contactFormRepository.save(contactForm);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        // Check if a ContactForm exists with the given institutecode
        return contactFormRepository.existsByInstitutecode(institutecode);
    }

    @Override
    public void deleteContactForm(Long id) {
        contactFormRepository.deleteById(id);
    }

    @Override
    public List<ContactForm> getAllContactForms() {
        // Fetch all ContactForms from the database
        return contactFormRepository.findAll();
    }

    @Override
    public ContactForm updateContactFormByInstitutecode(String institutecode, ContactForm updatedContactForm, MultipartFile contactImage) throws IOException {
        // Fetch the existing ContactForm by institutecode
        ContactForm existingContactForm = contactFormRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("ContactForm not found with institutecode: " + institutecode));

        // Update fields
        existingContactForm.setName(updatedContactForm.getName());
        existingContactForm.setMobileNo(updatedContactForm.getMobileNo());
        existingContactForm.setCourse(updatedContactForm.getCourse());
        existingContactForm.setDescription(updatedContactForm.getDescription());
        existingContactForm.setEmail(updatedContactForm.getEmail());
        existingContactForm.setMaps(updatedContactForm.getMaps());

        // Handle image upload
        if (contactImage != null && !contactImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(contactImage);
            existingContactForm.setContactImage(imageUrl);
        }

        // Save and return the updated ContactForm
        return contactFormRepository.save(existingContactForm);
    }

    @Override
    public Optional<ContactForm> getContactFormByInstitutecode(String institutecode) {
        // Fetch the ContactForm by institutecode
        return contactFormRepository.findByInstitutecode(institutecode);
    }
}
