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
    public FacilityTitle saveFacilityTitle(FacilityTitle facilityTitle) {
        return facilityTitleRepository.save(facilityTitle);
    }

    @Override
    public List<FacilityTitle> getAllFacilityTitles() {
        return facilityTitleRepository.findAll();
    }

    @Override
    public Optional<FacilityTitle> getFacilityTitleById(Long id) {
        return facilityTitleRepository.findById(id);
    }

    @Override
    public FacilityTitle updateFacilityTitle(Long id, FacilityTitle facilityTitle) {
        return facilityTitleRepository.findById(id).map(existingFacility -> {
            existingFacility.setFacilityTitle(facilityTitle.getFacilityTitle());
            return facilityTitleRepository.save(existingFacility);
        }).orElseThrow(() -> new RuntimeException("Facility Title not found"));
    }

    @Override
    public void deleteFacilityTitle(Long id) {
        facilityTitleRepository.deleteById(id);
    }
}