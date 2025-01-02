package com.WebApplication.Repository;

import com.WebApplication.Entity.Footer;
import com.WebApplication.Entity.MapAndImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MapAndImagesRepository extends JpaRepository<MapAndImages, Long> {

    boolean existsByInstitutecode(String institutecode);

    void deleteByInstitutecode(String institutecode);


    @Query("SELECT f FROM MapAndImages  f WHERE f.institutecode = :institutecode")
    Optional<MapAndImages> findByInstitutecode( @Param("institutecode") String institutecode);




}
