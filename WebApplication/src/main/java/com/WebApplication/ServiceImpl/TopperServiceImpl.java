package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Topper;
import com.WebApplication.Repository.TopperRepository;
import com.WebApplication.Service.S3Service;
import com.WebApplication.Service.TopperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TopperServiceImpl implements TopperService {

    @Autowired
    private TopperRepository topperRepository;

    @Autowired
    private S3Service s3Service;

    @Override
    public Topper createTopper(Topper topper, String institutecode, List<MultipartFile> topperImages) throws IOException {
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
    public Topper updateTopperById(Long topperId, List<MultipartFile> topperImages, String topperColor,
                                   String name, Double totalMarks, String post, Integer rank, Integer year) throws IOException {
        Optional<Topper> optionalTopper = topperRepository.findById(topperId);

        if (optionalTopper.isEmpty()) {
            throw new RuntimeException("Topper not found with ID: " + topperId);
        }

        Topper topper = optionalTopper.get();
        log.info("Found Topper: {}", topper);

        // ✅ Update images
        if (topperImages != null && !topperImages.isEmpty()) {
            log.info("Adding {} new images", topperImages.size());
            for (MultipartFile image : topperImages) {
                String newImageUrl = s3Service.uploadImage(image);
                topper.getTopperImages().add(newImageUrl);
                topper.getImageUrlIds().add(topper.getImageUrlIds().size() + 1);
            }
        }

        // ✅ Update topper color
        if (topperColor != null && !topperColor.isEmpty()) {
            log.info("Updating topper color from {} to {}", topper.getTopperColor(), topperColor);
            topper.setTopperColor(topperColor);
        }

        // ✅ Update other fields
        if (name != null && !name.isEmpty()) {
            topper.setName(name);
        }
        if (totalMarks != null) {
            topper.setTotalMarks(totalMarks);
        }
        if (post != null && !post.isEmpty()) {
            topper.setPost(post);
        }
        if (rank != null) {
            topper.setRank(rank);
        }
        if (year != null) {
            topper.setYear(year);
        }

        // ✅ Save and log the updated data
        Topper updatedTopper = topperRepository.save(topper);
        log.info("Updated Topper: {}", updatedTopper);

        return updatedTopper;
    }



    @Override
    public void deleteTopperById(Long topperId) {
        Optional<Topper> topperOptional = topperRepository.findById(topperId);

        if (topperOptional.isPresent()) {
            Topper topper = topperOptional.get();

            // ✅ Keep images but remove all other data
            topper.setTopperImages(null);
            topper.setImageUrlIds(null);
            topper.setTopperColor(null);
            topper.setName(null);
            topper.setTotalMarks(null);
            topper.setPost(null);
            topper.setRank(null);
            topper.setYear(null);
            topper.setInstitutecode(null);

            topperRepository.delete(topper);
            log.info("Deleted topper data for ID: {}", topperId);
        } else {
            throw new RuntimeException("Topper not found with ID: " + topperId);
        }
    }


    @Override
    public void deleteTopperByInstitutecode(String institutecode) {
        List<Topper> toppers = topperRepository.findByInstitutecode(institutecode);

        if (toppers.isEmpty()) {
            throw new RuntimeException("No toppers found for the given institutecode: " + institutecode);
        }

        topperRepository.deleteAll(toppers); // Delete all records
        log.info("Deleted all toppers for institutecode: {}", institutecode);
    }

    @Override
    public Optional<Topper> getTopperById(Long topperId) {
        return topperRepository.findById(topperId);
    }


    @Override
    public List<Topper> getAllToppersByInstitutecode(String institutecode) {
        return topperRepository.findByInstitutecode(institutecode);
    }


    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return topperRepository.existsByInstitutecode(institutecode);
    }
}
