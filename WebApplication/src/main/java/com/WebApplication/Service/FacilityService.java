package com.WebApplication.Service;

import com.WebApplication.Entity.Facility;

import java.util.Optional;

public interface FacilityService {

    // Method to save a Facility with institutecode
    Facility saveFacility(Facility facility, String institutecode);

    // Method to check if a Facility with institutecode already exists
    boolean existsByInstitutecode(String institutecode);

    // Method to delete a Facility by its ID
    void deleteFacility(Long facilityId);

    // Method to get a Facility by institutecode
    Optional<Facility> getFacilityByInstitutecode(String institutecode);

    // Method to get all Facilities for a given institutecode
    Optional<Facility> getAllFacilities(String institutecode);

    // Method to update a Facility by its institutecode
    Facility updateFacilityByInstitutecode(String institutecode, Facility updatedFacility);
}
