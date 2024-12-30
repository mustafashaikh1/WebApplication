package com.WebApplication.Service;

import com.WebApplication.Entity.Facility;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface FacilityService {

    Facility saveFacility(Facility facility, String institutecode, MultipartFile facilityImage) throws IOException;

    boolean existsByInstitutecode(String institutecode);

    void deleteFacility(Long facilityId);

    Optional<Facility> getFacilityByInstitutecode(String institutecode);

    Optional<Facility> getAllFacilities(String institutecode);

    Facility updateFacilityByInstitutecode(String institutecode, Facility updatedFacility, MultipartFile facilityImage) throws IOException;
}
