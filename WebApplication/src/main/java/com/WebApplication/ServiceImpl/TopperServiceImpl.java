package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Topper;
import com.WebApplication.Repository.TopperRepository;
import com.WebApplication.Service.S3Service;
import com.WebApplication.Service.TopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TopperServiceImpl implements TopperService {

    @Autowired
    private TopperRepository topperRepository;

    @Autowired
    private S3Service s3Service; // AWS S3 Service

    @Override
    public Topper createTopper(Topper topper, String institutecode, MultipartFile topperImage) throws IOException {
        topper.setInstitutecode(institutecode);

        if (topperImage != null && !topperImage.isEmpty()) {
            String imageUrl = s3Service.uploadImage(topperImage);
            topper.setTopperImage(imageUrl);
        }

        return topperRepository.save(topper);
    }

    @Override
    public Topper updateTopper(Long id, Topper topper, MultipartFile topperImage) throws IOException {
        Optional<Topper> existingTopper = topperRepository.findById(id);
        if (existingTopper.isPresent()) {
            Topper updatedTopper = existingTopper.get();

            // Update other fields
            updatedTopper.setName(topper.getName());
            updatedTopper.setTotalMarks(topper.getTotalMarks());
            updatedTopper.setPost(topper.getPost());
            updatedTopper.setRank(topper.getRank());
            updatedTopper.setYear(topper.getYear());
            updatedTopper.setTopperColor(topper.getTopperColor());

            // ✅ If a new image is provided, add it WITHOUT deleting the previous one
            if (topperImage != null && !topperImage.isEmpty()) {
                String newImageUrl = s3Service.uploadImage(topperImage);

                // Maintain both old and new images (Optional: You can use a List<String> if needed)
                if (updatedTopper.getTopperImage() != null && !updatedTopper.getTopperImage().isEmpty()) {
                    updatedTopper.setTopperImage(updatedTopper.getTopperImage() + "," + newImageUrl); // Append new image
                } else {
                    updatedTopper.setTopperImage(newImageUrl);
                }
            }

            return topperRepository.save(updatedTopper);
        } else {
            throw new RuntimeException("Topper not found with id: " + id);
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
