package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.ManuBar;
import com.WebApplication.Repository.ManuBarRepository;
import com.WebApplication.Service.ManuBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ManuBarServiceImpl implements ManuBarService {

    @Autowired
    private ManuBarRepository manuBarRepository;

    // Example for handling image upload (adjust as per your image service setup)
    private String uploadImage(MultipartFile file) throws IOException {
        // Implement image upload logic here (e.g., Cloudinary integration)
        // For example, return the uploaded image URL.
        return "https://example.com/your-uploaded-image.jpg";
    }

    @Override
    public ManuBar createManuBar(ManuBar manuBar, String institutecode, MultipartFile menubarImage) {
        try {
            if (menubarImage != null && !menubarImage.isEmpty()) {
                String imageUrl = uploadImage(menubarImage);
                manuBar.setImageUrl(imageUrl);
            }
            manuBar.setInstitutecode(institutecode);
            return manuBarRepository.save(manuBar);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Override
    public ManuBar updateManuBar(Long id, ManuBar manuBar) {
        Optional<ManuBar> existingManuBar = manuBarRepository.findById(id);
        if (existingManuBar.isPresent()) {
            ManuBar updatedManuBar = existingManuBar.get();
            updatedManuBar.setManuBarColor(manuBar.getManuBarColor());
            updatedManuBar.setImageUrl(manuBar.getImageUrl());
            return manuBarRepository.save(updatedManuBar);
        } else {
            throw new RuntimeException("ManuBar not found with id: " + id);
        }
    }

    @Override
    public void deleteManuBar(Long id) {
        manuBarRepository.deleteById(id);
    }

    @Override
    public ManuBar getManuBarById(Long id) {
        return manuBarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ManuBar not found with id: " + id));
    }

    @Override
    public List<ManuBar> getAllManuBars(String institutecode) {
        return manuBarRepository.findByInstitutecode(institutecode);
    }
}
