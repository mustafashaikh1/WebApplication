package com.WebApplication.Service;

import com.WebApplication.Entity.Facility;
import java.util.List;

public interface FacilityService {
    Facility saveFacility(Facility facility,String institutecode);
    Facility updateFacility(Long facilityId, Facility facility);
    Facility getFacilityById(Long facilityId);
    List<Facility> getAllFacilities(String institutecode);
    void deleteFacility(Long facilityId);
}
