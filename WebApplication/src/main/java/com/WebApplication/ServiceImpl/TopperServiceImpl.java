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
    private S3Service s3Service;

    @Override
    public Topper createTopper(Topper topper, String institutecode, List<MultipartFile> topperImages) throws IOException {
        if (topperRepository.existsByInstitutecode(institutecode)) {
            throw new RuntimeException("Topper with this institutecode already exists");
        }

        if (topperImages != null && !topperImages.isEmpty()) {
            MultipartFile image = topperImages.get(0); // Only storing a single image
            String imageUrl = s3Service.uploadImage(image);
            topper.setTopperImage(imageUrl);
        }

        return topperRepository.save(topper);
    }

    @Override
    public Topper updateTopperByTopperIdAndInstitutecode(Long topperId, String institutecode, List<MultipartFile> topperImages, String topperColor) throws IOException {
        Optional<Topper> optionalTopper = topperRepository.findByTopperIdAndInstitutecode(topperId, institutecode);

        if (optionalTopper.isPresent()) {
            Topper topper = optionalTopper.get();

            if (topperImages != null && !topperImages.isEmpty()) {
                MultipartFile image = topperImages.get(0); // Only storing a single image
                String newImageUrl = s3Service.uploadImage(image);
                topper.setTopperImage(newImageUrl);
            }

            if (topperColor != null && !topperColor.isEmpty()) {
                topper.setTopperColor(topperColor);
            }

            return topperRepository.save(topper);
        } else {
            throw new RuntimeException("Topper not found with topperId: " + topperId + " and institutecode: " + institutecode);
        }
    }

    @Override
    public void deleteTopperByTopperIdAndInstitutecode(Long topperId, String institutecode) {
        Optional<Topper> topperOptional = topperRepository.findByTopperIdAndInstitutecode(topperId, institutecode);

        if (topperOptional.isPresent()) {
            Topper topper = topperOptional.get();
            topperRepository.delete(topper);
        } else {
            throw new RuntimeException("Topper not found for the given topperId and institutecode.");
        }
    }

    @Override
    public void deleteTopperByInstitutecode(String institutecode) {
        Optional<Topper> topperOptional = topperRepository.findByInstitutecode(institutecode);

        if (topperOptional.isPresent()) {
            Topper topper = topperOptional.get();
            topperRepository.delete(topper);
        } else {
            throw new RuntimeException("Topper not found for the given institutecode.");
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
