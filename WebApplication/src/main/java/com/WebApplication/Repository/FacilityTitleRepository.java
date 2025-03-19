package com.WebApplication.Repository;

import com.WebApplication.Entity.FacilityTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityTitleRepository extends JpaRepository<FacilityTitle, Long> {
    Optional<FacilityTitle> findByFacilityTitleAndInstitutecode(String facilityTitle, String institutecode);
}