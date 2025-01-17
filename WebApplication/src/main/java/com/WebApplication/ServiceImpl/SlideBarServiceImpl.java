package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.SlideBar;
import com.WebApplication.Repository.SlideBarRepository;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.SlideBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SlideBarServiceImpl implements SlideBarService {

    @Autowired
    private SlideBarRepository slideBarRepository;

    @Autowired
    private CloudinaryService cloudinaryService;



    @Override
    public SlideBar createSlideBar(SlideBar slideBar, String institutecode, List<MultipartFile> slideImages) {
        boolean exists = slideBarRepository.existsByInstitutecode(institutecode);
        if (exists) {
            throw new RuntimeException("A SlideBar with institutecode '" + institutecode + "' already exists.");
        }

        try {
            if (slideImages != null && !slideImages.isEmpty()) {
                List<String> imageUrls = new ArrayList<>();
                for (MultipartFile slideImage : slideImages) {
                    String imageUrl = cloudinaryService.uploadImage(slideImage);
                    imageUrls.add(imageUrl);
                }
                slideBar.setSlideImages(imageUrls);
            }

            slideBar.setInstitutecode(institutecode);
            return slideBarRepository.save(slideBar);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload images", e);
        }
    }

    @Override
    public SlideBar updateSlideBarById(Long id, SlideBar updatedSlideBar, List<MultipartFile> slideImages) throws IOException {
        // Fetch the existing SlideBar by ID
        SlideBar existingSlideBar = slideBarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SlideBar not found with id: " + id));

        // Update slideBarColor if provided
        if (updatedSlideBar.getSlideBarColor() != null) {
            existingSlideBar.setSlideBarColor(updatedSlideBar.getSlideBarColor());
        }

        // Replace slideImages if a new list of images is provided
        if (slideImages != null && !slideImages.isEmpty()) {
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile slideImage : slideImages) {
                String imageUrl = cloudinaryService.uploadImage(slideImage);
                imageUrls.add(imageUrl);
            }
            existingSlideBar.setSlideImages(imageUrls); // Replace the existing images with the new ones
        }

        // Save and return the updated SlideBar
        return slideBarRepository.save(existingSlideBar);
    }

//    @Override
//    public SlideBar updateSlideBarByInstitutecode(String institutecode, SlideBar updatedSlideBar, List<MultipartFile> slideImages) throws IOException {
//        // Fetch the existing SlideBar
//        SlideBar existingSlideBar = slideBarRepository.findByInstitutecode(institutecode)
//                .orElseThrow(() -> new RuntimeException("SlideBar not found with institutecode: " + institutecode));
//
//        // Update slideBarColor if provided
//        if (updatedSlideBar.getSlideBarColor() != null) {
//            existingSlideBar.setSlideBarColor(updatedSlideBar.getSlideBarColor());
//        }
//
//        // Replace slideImages if a new list of images is provided
//        if (slideImages != null && !slideImages.isEmpty()) {
//            List<String> imageUrls = new ArrayList<>();
//            for (MultipartFile slideImage : slideImages) {
//                String imageUrl = cloudinaryService.uploadImage(slideImage);
//                imageUrls.add(imageUrl);
//            }
//            existingSlideBar.setSlideImages(imageUrls); // Replace the existing images with the new ones
//        }
//
//        // Save and return the updated SlideBar
//        return slideBarRepository.save(existingSlideBar);
//    }


    @Override
    public void deleteSlideBar(Long id) {
        slideBarRepository.deleteById(id);
    }

    @Override
    public Optional<SlideBar> getSlideBarByInstitutecode(String institutecode) {
        return slideBarRepository.findByInstitutecode(institutecode);
    }

    @Override
    public List<SlideBar> getAllSlideBars() {
        return slideBarRepository.findAll();
    }


    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return slideBarRepository.existsByInstitutecode(institutecode);
    }


}
