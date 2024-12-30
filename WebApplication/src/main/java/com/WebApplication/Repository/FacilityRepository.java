package com.WebApplication.Repository;

import com.WebApplication.Entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    @Query("SELECT f FROM Facility f WHERE f.institutecode = :institutecode")
    Optional<Facility> findByInstitutecode(@Param("institutecode") String institutecode);

    boolean existsByInstitutecode(String institutecode);
}
