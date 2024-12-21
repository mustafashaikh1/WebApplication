package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Facility;
import com.WebApplication.Repository.FacilityRepository;
import com.WebApplication.Service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;



    @Override
    public Facility saveFacility(Facility facility, String institutecode) {
        facility.setInstitutecode(institutecode); // Ensure the institutecode is set
        return facilityRepository.save(facility);
    }

    @Override
    public Facility updateFacility(Long facilityId, Facility facility) {
        Optional<Facility> existingFacility = facilityRepository.findById(facilityId);
        if (existingFacility.isPresent()) {
            Facility updatedFacility = existingFacility.get();
            updatedFacility.setFacilityName(facility.getFacilityName());
            updatedFacility.setExperienceInYear(facility.getExperienceInYear());
            updatedFacility.setSubject(facility.getSubject());
            updatedFacility.setInstitutecode(facility.getInstitutecode()); // Ensure the institutecode is updated
            return facilityRepository.save(updatedFacility);
        } else {
            throw new RuntimeException("Facility not found with id: " + facilityId);
        }
    }

    @Override
    public Facility getFacilityById(Long facilityId) {
        return facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + facilityId));
    }

    @Override
    public List<Facility> getAllFacilities(String institutecode) {
        return facilityRepository.findByInstitutecode(institutecode);
    }

    @Override
    public void deleteFacility(Long facilityId) {
        if (facilityRepository.existsById(facilityId)) {
            facilityRepository.deleteById(facilityId);
        } else {
            throw new RuntimeException("Facility not found with id: " + facilityId);
        }
    }
}
