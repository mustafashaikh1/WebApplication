package com.WebApplication.Repository;

import com.WebApplication.Entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    boolean existsByDynamicPart(String dynamicPart);

    UrlMapping findByDynamicPartAndInstitutecode(String dynamicPart, String institutecode); // Read with institutecode

    List<UrlMapping> findAllByInstitutecode(String institutecode); // Fetch all by institutecode
}
