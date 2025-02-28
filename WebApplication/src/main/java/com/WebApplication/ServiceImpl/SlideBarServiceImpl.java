package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.SlideBar;
import com.WebApplication.Repository.SlideBarRepository;
import com.WebApplication.Service.S3Service;
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
    private S3Service s3Service;

    @Override
    public SlideBar createSlideBar(SlideBar slideBar, String institutecode, List<MultipartFile> slideImages) throws IOException {
        if (slideBarRepository.existsByInstitutecode(institutecode)) {
            throw new RuntimeException("SlideBar with this institutecode already exists");
        }

        if (slideBar.getSlideImages() == null) {
            slideBar.setSlideImages(new ArrayList<>());
        }
        if (slideBar.getImageUrlIds() == null) {
            slideBar.setImageUrlIds(new ArrayList<>());
        }

        int imageUrlIdCounter = 1;

        if (slideImages != null && !slideImages.isEmpty()) {
            for (MultipartFile slideImage : slideImages) {
                // Upload image to S3 and get the URL
                String imageUrl = s3Service.uploadImage(slideImage);

                slideBar.getSlideImages().add(imageUrl);
                slideBar.getImageUrlIds().add(imageUrlIdCounter++);
            }
        }

        return slideBarRepository.save(slideBar);
    }

    @Override
    public SlideBar updateSlideBarByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode, List<MultipartFile> slideImages, String slideBarColor) throws IOException {
        Optional<SlideBar> optionalSlideBar = slideBarRepository.findByImageUrlIdAndInstitutecode(imageUrlId, institutecode);

        if (optionalSlideBar.isPresent()) {
            SlideBar slideBar = optionalSlideBar.get();

            // If new images are provided, upload and append them to the existing list
            if (slideImages != null && !slideImages.isEmpty()) {
                for (MultipartFile slideImage : slideImages) {
                    String newImageUrl = s3Service.uploadImage(slideImage);
                    slideBar.getSlideImages().add(newImageUrl);  // ✅ Append the new image
                    slideBar.getImageUrlIds().add(slideBar.getImageUrlIds().size() + 1);  // ✅ Assign new ID
                }
            }

            // Update slideBarColor if provided
            if (slideBarColor != null && !slideBarColor.isEmpty()) {
                slideBar.setSlideBarColor(slideBarColor);
            }

            return slideBarRepository.save(slideBar);
        } else {
            throw new RuntimeException("SlideBar not found with imageUrlId: " + imageUrlId + " and institutecode: " + institutecode);
        }
    }


    @Override
    public void deleteSlideBarByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode) {
        Optional<SlideBar> slideBarOptional = slideBarRepository.findByInstitutecode(institutecode);

        if (slideBarOptional.isPresent()) {
            SlideBar slideBar = slideBarOptional.get();

            int index = slideBar.getImageUrlIds().indexOf(imageUrlId.intValue());
            if (index == -1) {
                throw new RuntimeException("Image URL ID not found in the SlideBar.");
            }

            String imageUrl = slideBar.getSlideImages().get(index);

            try {
                s3Service.deleteImage(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete image from S3", e);
            }

            slideBar.getImageUrlIds().remove(index);
            slideBar.getSlideImages().remove(index);

            slideBarRepository.save(slideBar);
        } else {
            throw new RuntimeException("SlideBar not found for the given instituteCode.");
        }
    }

    @Override
    public Optional<SlideBar> getAllSlideBarsByInstitutecode(String institutecode) {
        return slideBarRepository.findByInstitutecode(institutecode);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return slideBarRepository.existsByInstitutecode(institutecode);
    }
}
