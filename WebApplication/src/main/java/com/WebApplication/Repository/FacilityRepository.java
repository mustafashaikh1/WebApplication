package com.WebApplication.Repository;

import com.WebApplication.Entity.Facility;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    List<Facility> findByInstitutecode(String institutecode);

    // Custom query to update facilityColor for all facilities with a specific institutecode
    @Modifying
    @Transactional
    @Query("UPDATE Facility f SET f.facilityColor = :facilityColor WHERE f.institutecode = :institutecode")
    void addFacilityColorByInstitutecode(String institutecode, String facilityColor);

    @Modifying
    @Transactional
    @Query("UPDATE Facility f SET f.facilityColor = :facilityColor WHERE f.institutecode = :institutecode")
    void updateFacilityColorByInstitutecode(String institutecode, String facilityColor);

    @Modifying
    @Transactional
    @Query("UPDATE Facility f SET f.facilityColor = NULL WHERE f.institutecode = :institutecode")
    void deleteFacilityColorByInstitutecode(String institutecode);


    @Query("SELECT f.facilityColor FROM Facility f WHERE f.institutecode = :institutecode")
    List<String> findFacilityColorByInstitutecode(@Param("institutecode") String institutecode);




}
