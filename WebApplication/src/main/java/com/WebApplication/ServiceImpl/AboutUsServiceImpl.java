package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.AboutUs;
import com.WebApplication.Repository.AboutUsRepository;
import com.WebApplication.Service.AboutUsService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsRepository aboutUsRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public AboutUs createAboutUs(AboutUs aboutUs, String institutecode, MultipartFile aboutUsImage) {
        if (aboutUsRepository.existsByInstitutecode(institutecode)) {
            throw new RuntimeException("AboutUs already exists for institutecode: " + institutecode);
        }

        aboutUs.setInstitutecode(institutecode);

        if (aboutUsImage != null && !aboutUsImage.isEmpty()) {
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(aboutUsImage.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("secure_url");
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
            existingAboutUs.setAboutUsTitle(aboutUs.getAboutUsTitle());
            existingAboutUs.setDescription(aboutUs.getDescription());

            if (aboutUsImage != null && !aboutUsImage.isEmpty()) {
                try {
                    Map<String, Object> uploadResult = cloudinary.uploader().upload(aboutUsImage.getBytes(), ObjectUtils.emptyMap());
                    String imageUrl = (String) uploadResult.get("secure_url");
                    existingAboutUs.setAboutUsImage(imageUrl);
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
        aboutUsRepository.deleteById(id);
    }

    @Override
    public AboutUs getAboutUsById(Long id) {
        return aboutUsRepository.findById(id).orElseThrow(() -> new RuntimeException("AboutUs not found with id: " + id));
    }

    @Override
    public Optional<AboutUs> getAllAboutUs(String institutecode) {
        return aboutUsRepository.findByInstitutecode(institutecode);
    }
}
