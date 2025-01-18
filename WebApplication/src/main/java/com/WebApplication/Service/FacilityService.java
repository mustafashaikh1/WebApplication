package com.WebApplication.Service;

import com.WebApplication.Entity.Facility;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FacilityService {
    Facility saveFacility(Facility facility, String institutecode, MultipartFile facilityImage) throws IOException;
    Facility updateFacility(Long facilityId, Facility facility, MultipartFile facilityImage) throws IOException;
    Facility getFacilityById(Long facilityId);
    List<Facility> getAllFacilities(String institutecode);
    void deleteFacility(Long facilityId);



    void addFacilityColorByInstitutecode(String institutecode, String facilityColor);

    void updateFacilityColorByInstitutecode(String institutecode, String facilityColor);

    void deleteFacilityColorByInstitutecode(String institutecode);

}
