package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.AboutUs;
import com.WebApplication.Repository.AboutUsRepository;
import com.WebApplication.Service.AboutUsService;
import com.WebApplication.Service.CloudinaryService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@Service
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsRepository aboutUsRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private CloudinaryService cloudinaryService;
    

    public AboutUs createAboutUs(AboutUs aboutUs, String institutecode, MultipartFile aboutUsImage) {
        aboutUs.setInstitutecode(institutecode);


        if (aboutUsImage != null && !aboutUsImage.isEmpty()) {
            try {
                // Upload image to Cloudinary and get the secure URL
                Map uploadResult = cloudinary.uploader().upload(aboutUsImage.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("secure_url");
                aboutUs.setAboutUsImage(imageUrl); // Set the uploaded image URL
            } catch (IOException e) {
                // Log the exception
                System.err.println("Error uploading image to Cloudinary: " + e.getMessage());
                // You can throw a custom exception or rethrow IOException
                throw new RuntimeException("Error uploading image", e);
            }
        }

        // Save AboutUs entity to the database
        return aboutUsRepository.save(aboutUs);
    }

    public AboutUs updateAboutUs(Long id, AboutUs aboutUs, MultipartFile aboutUsImage) {
        Optional<AboutUs> existingAboutUsOpt = aboutUsRepository.findById(id);
        if (existingAboutUsOpt.isPresent()) {
            AboutUs existingAboutUs = existingAboutUsOpt.get();
            existingAboutUs.setAboutUsTitle(aboutUs.getAboutUsTitle());
            existingAboutUs.setDescription(aboutUs.getDescription());

            // Upload a new image if provided
            if (aboutUsImage != null && !aboutUsImage.isEmpty()) {
                try {
                    Map uploadResult = cloudinary.uploader().upload(aboutUsImage.getBytes(), ObjectUtils.emptyMap());
                    String imageUrl = (String) uploadResult.get("secure_url");
                    existingAboutUs.setAboutUsImage(imageUrl); // Update the image URL
                } catch (IOException e) {
                    // Log the exception
                    System.err.println("Error uploading image to Cloudinary: " + e.getMessage());
                    throw new RuntimeException("Error uploading image", e);
                }
            }

            // Save the updated AboutUs entity
            return aboutUsRepository.save(existingAboutUs);
        } else {
            throw new RuntimeException("AboutUs not found with ID: " + id);
        }
    }

    @Override
    public void deleteAboutUs(Long id) {
        aboutUsRepository.deleteById(id);
    }

    @Override
    public AboutUs getAboutUsById(Long id) {
        return aboutUsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AboutUs not found with id: " + id));
    }

    @Override
    public List<AboutUs> getAllAboutUs(String institutecode) {
        return aboutUsRepository.findByInstitutecode(institutecode);  // Fetch AboutUs by institutecode
    }
}
