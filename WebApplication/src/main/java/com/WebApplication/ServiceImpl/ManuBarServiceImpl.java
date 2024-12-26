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
        try {
            // Upload the image if it's provided
            if (menubarImage != null && !menubarImage.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(menubarImage);
                manuBar.setMenubarImage(imageUrl); // Store the image URL
            }
            manuBar.setInstitutecode(institutecode); // Set the institutecode
            return manuBarRepository.save(manuBar); // Save to database
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

//    @Override
//    public ManuBar updateManuBar(Long id, ManuBar manuBar) {
//        Optional<ManuBar> existingManuBar = manuBarRepository.findById(id);
//        if (existingManuBar.isPresent()) {
//            ManuBar updatedManuBar = existingManuBar.get();
//            updatedManuBar.setManuBarColor(manuBar.getManuBarColor()); // Update the color
//            updatedManuBar.setMenuItems(manuBar.getMenuItems()); // Update the menu items
//
//            // If an image is provided, update the image URL
//            if (manuBar.getMenubarImage() != null) {
//                updatedManuBar.setMenubarImage(manuBar.getMenubarImage());
//            }
//
//            return manuBarRepository.save(updatedManuBar); // Save updated ManuBar
//        } else {
//            throw new RuntimeException("ManuBar not found with id: " + id);
//        }
//    }


    @Override
    public ManuBar updateManuBarByInstitutecode(String institutecode, ManuBar manuBar) {
        List<ManuBar> manuBars = manuBarRepository.findByInstitutecode(institutecode);

        if (manuBars.isEmpty()) {
            throw new RuntimeException("ManuBar not found with institutecode: " + institutecode);
        }

        // Assuming you want to update the first matching ManuBar
        ManuBar existingManuBar = manuBars.get(0);
        existingManuBar.setManuBarColor(manuBar.getManuBarColor());
        existingManuBar.setMenubarImage(manuBar.getMenubarImage());
        existingManuBar.setMenuItems(manuBar.getMenuItems());
        return manuBarRepository.save(existingManuBar);
    }



    @Override
    public void deleteManuBar(Long id) {
        manuBarRepository.deleteById(id);
    }



    @Override
    public List<ManuBar> getManuBarByInstitutecode(String institutecode) {
        return manuBarRepository.findByInstitutecode(institutecode);
    }


    @Override
    public List<ManuBar> getAllManuBars() {
        return manuBarRepository.findAll(); // Fetch all ManuBar records from the database
    }

}
