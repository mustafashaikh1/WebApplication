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
    private S3Service s3Service;

    @Override
    public Topper createTopper(Topper topper, String institutecode, List<MultipartFile> topperImages) throws IOException {
        if (topperRepository.existsByInstitutecode(institutecode)) {
            throw new RuntimeException("Topper with this institutecode already exists");
        }

        if (topper.getTopperImages() == null) {
            topper.setTopperImages(new ArrayList<>());
        }
        if (topper.getImageUrlIds() == null) {
            topper.setImageUrlIds(new ArrayList<>());
        }

        int imageUrlIdCounter = 1;

        if (topperImages != null && !topperImages.isEmpty()) {
            for (MultipartFile image : topperImages) {
                String imageUrl = s3Service.uploadImage(image);
                topper.getTopperImages().add(imageUrl);
                topper.getImageUrlIds().add(imageUrlIdCounter++);
            }
        }

        return topperRepository.save(topper);
    }

    @Override
    public Topper updateTopperByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode, List<MultipartFile> topperImages, String topperColor) throws IOException {
        Optional<Topper> optionalTopper = topperRepository.findByImageUrlIdAndInstitutecode(imageUrlId, institutecode);

        if (optionalTopper.isPresent()) {
            Topper topper = optionalTopper.get();

            if (topperImages != null && !topperImages.isEmpty()) {
                for (MultipartFile image : topperImages) {
                    String newImageUrl = s3Service.uploadImage(image);
                    topper.getTopperImages().add(newImageUrl);
                    topper.getImageUrlIds().add(topper.getImageUrlIds().size() + 1);
                }
            }

            if (topperColor != null && !topperColor.isEmpty()) {
                topper.setTopperColor(topperColor);
            }

            return topperRepository.save(topper);
        } else {
            throw new RuntimeException("Topper not found with imageUrlId: " + imageUrlId + " and institutecode: " + institutecode);
        }
    }

    @Override
    public void deleteTopperByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode) {
        Optional<Topper> topperOptional = topperRepository.findByInstitutecode(institutecode);

        if (topperOptional.isPresent()) {
            Topper topper = topperOptional.get();

            if (topper.getImageUrlIds() == null || topper.getTopperImages() == null) {
                throw new RuntimeException("No images found for this topper.");
            }

            int index = topper.getImageUrlIds().indexOf(imageUrlId.intValue());
            if (index == -1) {
                throw new RuntimeException("Image URL ID not found in the Topper.");
            }

            // Remove the image ID from the database but do NOT delete the image from S3
            topper.getImageUrlIds().remove(index);
            topper.getTopperImages().remove(index);

            // Save updated topper (data only)
            topperRepository.save(topper);

        } else {
            throw new RuntimeException("Topper not found for the given instituteCode.");
        }
    }

    @Override
    public void deleteTopperByInstitutecode(String institutecode) {
        Optional<Topper> topperOptional = topperRepository.findByInstitutecode(institutecode);

        if (topperOptional.isPresent()) {
            Topper topper = topperOptional.get();

            // Keep images but delete the topper data
            topper.setTopperImages(null);
            topper.setImageUrlIds(null);
            topper.setTopperColor(null);
            topper.setName(null);
            topper.setTotalMarks(null);
            topper.setPost(null);
            topper.setRank(null);
            topper.setYear(null);

            // Delete the entire topper record
            topperRepository.delete(topper);
        } else {
            throw new RuntimeException("Topper not found for the given instituteCode.");
        }
    }

    @Override
    public Optional<Topper> getAllToppersByInstitutecode(String institutecode) {
        return topperRepository.findByInstitutecode(institutecode);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return topperRepository.existsByInstitutecode(institutecode);
    }
}
