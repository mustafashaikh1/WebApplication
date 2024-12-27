package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.ManuBar;
import com.WebApplication.Repository.ManuBarRepository;
import com.WebApplication.Service.CloudinaryService;
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

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public ManuBar createManuBar(ManuBar manuBar, String institutecode, MultipartFile menubarImage) {
        // Check if a ManuBar with the same institutecode already exists
        if (manuBarRepository.existsByInstitutecode(institutecode)) {
            throw new RuntimeException("A ManuBar with institutecode '" + institutecode + "' already exists.");
        }

        try {
            if (menubarImage != null && !menubarImage.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(menubarImage);
                manuBar.setMenubarImage(imageUrl);
            }
            manuBar.setInstitutecode(institutecode);
            return manuBarRepository.save(manuBar);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Override
    public ManuBar updateManuBarByInstitutecode(String institutecode, ManuBar updatedManuBar, MultipartFile menubarImage) throws IOException {
        ManuBar existingManuBar = manuBarRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("ManuBar not found with institutecode: " + institutecode));

        existingManuBar.setManuBarColor(updatedManuBar.getManuBarColor());

        if (menubarImage != null && !menubarImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(menubarImage);
            existingManuBar.setMenubarImage(imageUrl);
        }

        return manuBarRepository.save(existingManuBar);
    }

    @Override
    public void deleteManuBar(Long id) {
        manuBarRepository.deleteById(id);
    }

    @Override
    public Optional<ManuBar> getManuBarByInstitutecode(String institutecode) {
        return manuBarRepository.findByInstitutecode(institutecode);
    }

    @Override
    public List<ManuBar> getAllManuBars() {
        return manuBarRepository.findAll();
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return manuBarRepository.existsByInstitutecode(institutecode);
    }
}
