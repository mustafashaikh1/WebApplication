package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.AwardsAndAccolades;
import com.WebApplication.Repository.AwardsAndAccoladesRepository;
import com.WebApplication.Service.AwardsAndAccoladesService;
import com.WebApplication.Service.CloudinaryService;
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
    private CloudinaryService cloudinaryService;

    @Override
    public AwardsAndAccolades saveAward(AwardsAndAccolades award, String institutecode, MultipartFile awardImage) throws IOException {
        award.setInstitutecode(institutecode);
        if (awardImage != null && !awardImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(awardImage);
            award.setAwardImage(imageUrl);
        }
        return awardsAndAccoladesRepository.save(award);
    }

    @Override
    public AwardsAndAccolades updateAward(Long id, AwardsAndAccolades award, MultipartFile awardImage) throws IOException {
        Optional<AwardsAndAccolades> existingAward = awardsAndAccoladesRepository.findById(id);
        if (existingAward.isPresent()) {
            AwardsAndAccolades updatedAward = existingAward.get();
            updatedAward.setAwardName(award.getAwardName());
            updatedAward.setDescription(award.getDescription());
            updatedAward.setAwardedBy(award.getAwardedBy());
            updatedAward.setYear(award.getYear());
            updatedAward.setAwardTo(award.getAwardTo());
            updatedAward.setInstitutecode(award.getInstitutecode());
            if (awardImage != null && !awardImage.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(awardImage);
                updatedAward.setAwardImage(imageUrl);
            }
            return awardsAndAccoladesRepository.save(updatedAward);
        } else {
            throw new RuntimeException("Award not found");
        }
    }

    @Override
    public void deleteAward(Long id) {
        awardsAndAccoladesRepository.deleteById(id);
    }

    @Override
    public AwardsAndAccolades getAwardById(Long id) {
        return awardsAndAccoladesRepository.findById(id).orElseThrow(() -> new RuntimeException("Award not found"));
    }

    @Override
    public List<AwardsAndAccolades> getAllAwards(String institutecode) {
        return awardsAndAccoladesRepository.findAll(); // Add filtering by institutecode if required
    }
}
