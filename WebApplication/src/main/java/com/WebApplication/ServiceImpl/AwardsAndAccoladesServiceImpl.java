package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.AwardsAndAccolades;
import com.WebApplication.Repository.AwardsAndAccoladesRepository;
import com.WebApplication.Service.AwardsAndAccoladesService;
import com.WebApplication.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AwardsAndAccoladesServiceImpl implements AwardsAndAccoladesService {

    @Autowired
    private AwardsAndAccoladesRepository awardsAndAccoladesRepository;

    @Autowired
    private S3Service s3Service;  // Replacing Cloudinary with S3Service

    @Override
    public AwardsAndAccolades saveAward(AwardsAndAccolades award, String institutecode, MultipartFile awardImage) throws IOException {
        award.setInstitutecode(institutecode);

        if (awardImage != null && !awardImage.isEmpty()) {
            String imageUrl = s3Service.uploadImage(awardImage);  // Upload image to S3
            award.setAwardImage(imageUrl);
        }

        return awardsAndAccoladesRepository.save(award);
    }

    @Override
    public AwardsAndAccolades updateAward(Long id, AwardsAndAccolades award, MultipartFile awardImage) throws IOException {
        Optional<AwardsAndAccolades> existingAwardOpt = awardsAndAccoladesRepository.findById(id);

        if (existingAwardOpt.isPresent()) {
            AwardsAndAccolades existingAward = existingAwardOpt.get();

            // Update text fields
            existingAward.setAwardName(award.getAwardName());
            existingAward.setDescription(award.getDescription());
            existingAward.setAwardedBy(award.getAwardedBy());
            existingAward.setYear(award.getYear());
            existingAward.setAwardTo(award.getAwardTo());
            existingAward.setInstitutecode(award.getInstitutecode());

            // If a new image is provided, update it; otherwise, retain the old one
            if (awardImage != null && !awardImage.isEmpty()) {
                String imageUrl = s3Service.uploadImage(awardImage);
                existingAward.setAwardImage(imageUrl);
            }

            return awardsAndAccoladesRepository.save(existingAward);
        } else {
            throw new RuntimeException("Award not found with ID: " + id);
        }
    }


    @Override
    public void deleteAward(Long id) {
        Optional<AwardsAndAccolades> award = awardsAndAccoladesRepository.findById(id);

        if (award.isPresent()) {
            // Delete associated image from S3 if it exists
            if (award.get().getAwardImage() != null) {
                s3Service.deleteImage(award.get().getAwardImage());
            }
            awardsAndAccoladesRepository.deleteById(id);
        } else {
            throw new RuntimeException("Award not found");
        }
    }

    @Override
    public AwardsAndAccolades getAwardById(Long id) {
        return awardsAndAccoladesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Award not found"));
    }

    @Override
    public List<AwardsAndAccolades> getAllAwards(String institutecode) {
        return awardsAndAccoladesRepository.findAll(); // Add filtering by institutecode if required
    }
}
