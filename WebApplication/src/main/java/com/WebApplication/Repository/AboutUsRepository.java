package com.WebApplication.Repository;

import com.WebApplication.Entity.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AboutUsRepository extends JpaRepository<AboutUs, Long> {

    @Query("SELECT m FROM AboutUs m WHERE m.institutecode = :institutecode")
    List<AboutUs> findByInstitutecode(String institutecode);

}
