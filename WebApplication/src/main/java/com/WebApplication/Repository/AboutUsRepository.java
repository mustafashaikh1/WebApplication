package com.WebApplication.Repository;

import com.WebApplication.Entity.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs, Long> {

    @Query("SELECT m FROM AboutUs m WHERE m.institutecode = :institutecode")
    Optional<AboutUs> findByInstitutecode(String institutecode);


    boolean existsByInstitutecode(String institutecode);

}
