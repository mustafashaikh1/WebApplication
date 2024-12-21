package com.WebApplication.Repository;

import com.WebApplication.Entity.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AboutUsRepository extends JpaRepository<AboutUs, Long> {
    List<AboutUs> findByInstitutecode(String institutecode);

}
