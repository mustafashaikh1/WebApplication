package com.WebApplication.Repository;

import com.WebApplication.Entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM UrlMapping u WHERE u.dynamicPart = :dynamicPart")
    boolean existsByDynamicPart(@Param("dynamicPart") String dynamicPart);

    UrlMapping findByDynamicPartAndInstitutecode(String dynamicPart, String institutecode); // Read with institutecode

    List<UrlMapping> findAllByInstitutecode(String institutecode);


}
