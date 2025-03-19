package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.FacilityTitle;
import com.WebApplication.Repository.FacilityTitleRepository;
import com.WebApplication.Service.FacilityTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityTitleServiceImpl implements FacilityTitleService {

    @Autowired
    private FacilityTitleRepository facilityTitleRepository;

    @Override
    public FacilityTitle saveFacilityTitle(FacilityTitle facilityTitle, String institutecode) {
        // Check if a facility title with the same name and institutecode already exists
        Optional<FacilityTitle> existingFacility = facilityTitleRepository.findByFacilityTitleAndInstitutecode(
                facilityTitle.getFacilityTitle(), institutecode);

        if (existingFacility.isPresent()) {
            throw new RuntimeException("Facility Title already exists for this institution code.");
        }

        facilityTitle.setInstitutecode(institutecode);
        return facilityTitleRepository.save(facilityTitle);
    }

    @Override
    public List<FacilityTitle> getAllFacilityTitles(String institutecode) {
        return facilityTitleRepository.findAll();
    }

    @Override
    public Optional<FacilityTitle> getFacilityTitleById(Long id) {
        return facilityTitleRepository.findById(id);
    }

    @Override
    public FacilityTitle updateFacilityTitle(Long id, FacilityTitle facilityTitle) {
        FacilityTitle existingFacility=facilityTitleRepository.findById(id).orElseThrow(()->new RuntimeException("Facility Title not found"));
       existingFacility.setFacilityTitle(facilityTitle.getFacilityTitle());
       existingFacility.setInstitutecode(facilityTitle.getInstitutecode());
       return facilityTitleRepository.save(existingFacility);
    }

    @Override
    public void deleteFacilityTitle(Long id) {
        facilityTitleRepository.deleteById(id);
    }
}