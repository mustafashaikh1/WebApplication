package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Topper;
import com.WebApplication.Repository.TopperRepository;
import com.WebApplication.Service.S3Service;
import com.WebApplication.Service.TopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopperServiceImpl implements TopperService {

    @Autowired
    private TopperRepository topperRepository;

    @Autowired
    private S3Service s3Service; // AWS S3 Service

    @Override
    public Topper createTopper(Topper topper, String institutecode, List<MultipartFile> topperImages) throws IOException {
        if (topperRepository.existsByInstitutecode(institutecode)) {
            throw new RuntimeException("Topper with this institutecode already exists");
        }

        if (topper.getTopperImages() == null) {
            topper.setTopperImages(new ArrayList<>());
        }

        int imageCounter = 1;

        if (topperImages != null && !topperImages.isEmpty()) {
            for (MultipartFile image : topperImages) {
                String imageUrl = s3Service.uploadImage(image);
                topper.getTopperImages().add(imageUrl);
                imageCounter++;
            }
        }

        topper.setInstitutecode(institutecode);

        return topperRepository.save(topper);
    }


    @Override
    public Topper updateTopperByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode, List<MultipartFile> topperImages, String topperColor) throws IOException {
        Optional<Topper> optionalTopper = topperRepository.findByTopperImageAndInstitutecode(imageUrlId, institutecode);

        if (optionalTopper.isPresent()) {
            Topper topper = optionalTopper.get();

            // Append new images without deleting the previous ones
            if (topperImages != null && !topperImages.isEmpty()) {
                for (MultipartFile image : topperImages) {
                    String newImageUrl = s3Service.uploadImage(image);
                    topper.setTopperImage(topper.getTopperImage() + "," + newImageUrl); // Append images
                }
            }

            // Update topper color if provided
            if (topperColor != null && !topperColor.isEmpty()) {
                topper.setTopperColor(topperColor);
            }

            return topperRepository.save(topper);
        } else {
            throw new RuntimeException("Topper not found with imageUrlId: " + imageUrlId + " and institutecode: " + institutecode);
        }
    }




    @Override
    public void deleteTopper(Long id) {
        Optional<Topper> topper = topperRepository.findById(id);
        if (topper.isPresent()) {
            // Delete associated image from S3 if it exists
            if (topper.get().getTopperImage() != null) {
                s3Service.deleteImage(topper.get().getTopperImage());
            }
            topperRepository.deleteById(id);
        } else {
            throw new RuntimeException("Topper not found with id: " + id);
        }
    }

    @Override
    public Topper getTopperById(Long id) {
        return topperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topper not found with id: " + id));
    }

    @Override
    public List<Topper> getAllToppers(String institutecode) {
        return topperRepository.findByInstitutecode(institutecode);
    }
}
