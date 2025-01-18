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
    public SlideBar createSlideBar(SlideBar slideBar, String institutecode, List<MultipartFile> slideImages) throws IOException {
        if (slideBarRepository.existsByInstitutecode(institutecode)) {
            throw new RuntimeException("SlideBar with this institutecode already exists");
        }

        // Initialize the lists if they are null
        if (slideBar.getSlideImages() == null) {
            slideBar.setSlideImages(new ArrayList<>());
        }
        if (slideBar.getImageUrlIds() == null) {
            slideBar.setImageUrlIds(new ArrayList<>());
        }

        // Generate auto-incremented integer IDs for imageUrlIds
        int imageUrlIdCounter = 1;

        if (slideImages != null && !slideImages.isEmpty()) {
            for (MultipartFile slideImage : slideImages) {
                // Upload image to Cloudinary and get the URL
                String imageUrl = cloudinaryService.uploadImage(slideImage);

                // Generate auto-incremented ID
                slideBar.getSlideImages().add(imageUrl);
                slideBar.getImageUrlIds().add(imageUrlIdCounter++); // Add incrementing ID
            }
        }

        return slideBarRepository.save(slideBar);
    }



//    @Override
//    public SlideBar updateSlideBarById(Long id, SlideBar updatedSlideBar, List<MultipartFile> slideImages) throws IOException {
//        Optional<SlideBar> existingSlideBar = slideBarRepository.findById(id);
//
//        if (!existingSlideBar.isPresent()) {
//            throw new RuntimeException("SlideBar not found with id: " + id);
//        }
//
//        SlideBar slideBar = existingSlideBar.get();
//
//        // Update the color if provided
//        if (updatedSlideBar.getSlideBarColor() != null) {
//            slideBar.setSlideBarColor(updatedSlideBar.getSlideBarColor());
//        }
//
//        // If new images are uploaded, handle image_urlId updates
//        if (slideImages != null && !slideImages.isEmpty()) {
//            List<String> imageUrls = slideBar.getSlideImages();
//            List<Integer> imageUrlIds = slideBar.getImageUrlIds();
//
//            for (MultipartFile slideImage : slideImages) {
//                // Upload the image to Cloudinary and get the URL
//                String imageUrl = cloudinaryService.uploadImage(slideImage);
//                String imageUrlId = extractImageUrlId(imageUrl); // Extract image ID from the URL
//
//                imageUrls.add(imageUrl);
//                imageUrlIds.add(Integer.valueOf(imageUrlId));
//            }
//
//            slideBar.setSlideImages(imageUrls);
//            slideBar.setImageUrlIds(imageUrlIds);
//        }
//
//        return slideBarRepository.save(slideBar);
//    }




    @Override
    public SlideBar updateSlideBarByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode, List<MultipartFile> slideImages, String slideBarColor) throws IOException {
        // Find SlideBar by imageUrlId and institutecode
        Optional<SlideBar> optionalSlideBar = slideBarRepository.findByImageUrlIdAndInstitutecode(imageUrlId, institutecode);

        if (optionalSlideBar.isPresent()) {
            SlideBar slideBar = optionalSlideBar.get();

            // Find the index of the imageUrlId to update
            int indexToUpdate = slideBar.getImageUrlIds().indexOf(imageUrlId.intValue());

            if (indexToUpdate != -1) {
                // If new images are provided, update the specific image
                if (slideImages != null && !slideImages.isEmpty()) {
                    // Upload new image and get the URL
                    String imageUrl = cloudinaryService.uploadImage(slideImages.get(0)); // Only update the first image

                    // Update the particular image URL and ID
                    slideBar.getSlideImages().set(indexToUpdate, imageUrl);  // Replace the old image with the new one
                    slideBar.getImageUrlIds().set(indexToUpdate, imageUrlId.intValue());  // Ensure the image ID remains the same or update it if needed
                }

                // Update slideBarColor if provided
                if (slideBarColor != null && !slideBarColor.isEmpty()) {
                    slideBar.setSlideBarColor(slideBarColor);  // Update slideBar color
                }

                // Save and return the updated SlideBar
                return slideBarRepository.save(slideBar);
            } else {
                throw new RuntimeException("Image URL ID not found in the SlideBar for given institutecode.");
            }
        } else {
            throw new RuntimeException("SlideBar not found with imageUrlId: " + imageUrlId + " and institutecode: " + institutecode);
        }
    }





    // Extract the image URL ID from the Cloudinary URL (example: image_id from URL)
    private String extractImageUrlId(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));
    }

    @Override
    public void deleteSlideBar(Long id) {
        slideBarRepository.deleteById(id);
    }






//    @Override
//    public List<SlideBar> getAllSlideBars() {
//        return slideBarRepository.findAll();
//    }



    @Override
    public Optional<SlideBar> getAllSlideBarsByInstitutecode(String institutecode) {
        // Fetch all SlideBars by institutecode from the repository
        return slideBarRepository.findByInstitutecode(institutecode);
    }


    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return slideBarRepository.existsByInstitutecode(institutecode);
    }


}
