package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.AboutUs;
import com.WebApplication.Repository.AboutUsRepository;
import com.WebApplication.Service.AboutUsService;
import com.WebApplication.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsRepository aboutUsRepository;

    @Autowired
    private S3Service s3Service;  // AWS S3 Service

    @Override
    public AboutUs createAboutUs(AboutUs aboutUs, String institutecode, MultipartFile aboutUsImage) {
        if (aboutUsRepository.existsByInstitutecode(institutecode)) {
            throw new RuntimeException("AboutUs already exists for institutecode: " + institutecode);
        }

        aboutUs.setInstitutecode(institutecode);

        if (aboutUsImage != null && !aboutUsImage.isEmpty()) {
            try {
                String imageUrl = s3Service.uploadImage(aboutUsImage);  // Upload image to S3
                aboutUs.setAboutUsImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image", e);
            }
        }

        return aboutUsRepository.save(aboutUs);
    }

    @Override
    public AboutUs updateAboutUs(Long id, AboutUs aboutUs, MultipartFile aboutUsImage) {
        Optional<AboutUs> existingAboutUsOpt = aboutUsRepository.findById(id);

        if (existingAboutUsOpt.isPresent()) {
            AboutUs existingAboutUs = existingAboutUsOpt.get();

            // ✅ Update text fields
            existingAboutUs.setAboutUsTitle(aboutUs.getAboutUsTitle());
            existingAboutUs.setDescription(aboutUs.getDescription());

            // ✅ If a new image is provided, add it WITHOUT deleting the previous one
            if (aboutUsImage != null && !aboutUsImage.isEmpty()) {
                try {
                    // Upload new image to S3
                    String newImageUrl = s3Service.uploadImage(aboutUsImage);

                    // Append the new image to the existing image URLs (comma-separated)
                    if (existingAboutUs.getAboutUsImage() != null && !existingAboutUs.getAboutUsImage().isEmpty()) {
                        existingAboutUs.setAboutUsImage(existingAboutUs.getAboutUsImage() + "," + newImageUrl);
                    } else {
                        existingAboutUs.setAboutUsImage(newImageUrl);
                    }

                } catch (IOException e) {
                    throw new RuntimeException("Error uploading image", e);
                }
            }

            return aboutUsRepository.save(existingAboutUs);
        } else {
            throw new RuntimeException("AboutUs not found with ID: " + id);
        }
    }


    @Override
    public void deleteAboutUs(Long id) {
        Optional<AboutUs> aboutUs = aboutUsRepository.findById(id);

        if (aboutUs.isPresent()) {
            // Delete associated image from S3 if exists
            if (aboutUs.get().getAboutUsImage() != null) {
                s3Service.deleteImage(aboutUs.get().getAboutUsImage());
            }
            aboutUsRepository.deleteById(id);
        } else {
            throw new RuntimeException("AboutUs not found with ID: " + id);
        }
    }

    @Override
    public AboutUs getAboutUsById(Long id) {
        return aboutUsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AboutUs not found with ID: " + id));
    }

    @Override
    public Optional<AboutUs> getAllAboutUs(String institutecode) {
        return aboutUsRepository.findByInstitutecode(institutecode);
    }
}
