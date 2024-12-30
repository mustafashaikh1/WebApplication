package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Facility;
import com.WebApplication.Repository.FacilityRepository;
import com.WebApplication.Service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public Facility saveFacility(Facility facility, String institutecode) {
        if (existsByInstitutecode(institutecode)) {
            throw new RuntimeException("A Facility with institutecode '" + institutecode + "' already exists.");
        }
        facility.setInstitutecode(institutecode);
        return facilityRepository.save(facility);
    }

    @Override
    public Facility updateFacilityByInstitutecode(String institutecode, Facility updatedFacility) {
        Facility existingFacility = facilityRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Facility not found with institutecode: " + institutecode));

        existingFacility.setFacilityName(updatedFacility.getFacilityName());
        existingFacility.setExperienceInYear(updatedFacility.getExperienceInYear());
        existingFacility.setFacilityEducation(updatedFacility.getFacilityEducation());
        existingFacility.setSubject(updatedFacility.getSubject());

        return facilityRepository.save(existingFacility);
    }

    @Override
    public void deleteFacility(Long facilityId) {
        facilityRepository.deleteById(facilityId);
    }

    @Override
    public Optional<Facility> getAllFacilities(String institutecode) {
        return facilityRepository.findByInstitutecode(institutecode);
    }

    @Override
    public Optional<Facility> getFacilityByInstitutecode(String institutecode) {
        return facilityRepository.findByInstitutecode(institutecode);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return facilityRepository.existsByInstitutecode(institutecode);
    }
}
