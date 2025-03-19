package com.WebApplication.Service;

import com.WebApplication.Entity.FacilityTitle;

import java.util.List;
import java.util.Optional;

public interface FacilityTitleService {
    FacilityTitle saveFacilityTitle(FacilityTitle facilityTitle);
    List<FacilityTitle> getAllFacilityTitles();
    Optional<FacilityTitle> getFacilityTitleById(Long id);
    FacilityTitle updateFacilityTitle(Long id, FacilityTitle facilityTitle);
    void deleteFacilityTitle(Long id);
}
