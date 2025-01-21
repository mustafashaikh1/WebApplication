package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Facility;
import com.WebApplication.Repository.FacilityRepository;
import com.WebApplication.Service.CloudinaryService;
import com.WebApplication.Service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public Facility saveFacility(Facility facility, String institutecode, MultipartFile facilityImage) throws IOException {
        facility.setInstitutecode(institutecode);

        // Fetch the existing facility color for the institutecode (if any)
        List<String> existingFacilityColors = facilityRepository.findFacilityColorByInstitutecode(institutecode);

        // If colors exist, set the first one (or handle it based on your business logic)
        if (!existingFacilityColors.isEmpty()) {
            facility.setFacilityColor(existingFacilityColors.get(0)); // Set the first color found
        }

        // Handle facility image upload (if any)
        if (facilityImage != null && !facilityImage.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(facilityImage); // Assuming cloudinaryService is correctly implemented
            facility.setFacilityImage(imageUrl);
        }

        // Save the facility
        return facilityRepository.save(facility);
    }


    @Override
    public Facility updateFacility(Long facilityId, Facility facility, MultipartFile facilityImage) throws IOException {
        Optional<Facility> existingFacilityOpt = facilityRepository.findById(facilityId);
        if (existingFacilityOpt.isPresent()) {
            Facility existingFacility = existingFacilityOpt.get();

            // Update fields from the incoming facility object
            existingFacility.setFacilityName(facility.getFacilityName());
            existingFacility.setExperienceInYear(facility.getExperienceInYear());
            existingFacility.setSubject(facility.getSubject());
            existingFacility.setFacilityEducation(facility.getFacilityEducation());
            existingFacility.setDescription(facility.getDescription());

            // Retain or update the image
            if (facilityImage != null && !facilityImage.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(facilityImage);
                existingFacility.setFacilityImage(imageUrl);
            }

            return facilityRepository.save(existingFacility);
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



    @Override
    public void addFacilityColorByInstitutecode(String institutecode, String facilityColor) {
        facilityRepository.addFacilityColorByInstitutecode(institutecode, facilityColor);
    }

    @Override
    public void updateFacilityColorByInstitutecode(String institutecode, String facilityColor) {
        facilityRepository.updateFacilityColorByInstitutecode(institutecode, facilityColor);
    }

    @Override
    public void deleteFacilityColorByInstitutecode(String institutecode) {
        facilityRepository.deleteFacilityColorByInstitutecode(institutecode);
    }


}
