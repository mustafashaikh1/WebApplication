package com.WebApplication.Repository;

import com.WebApplication.Entity.Footer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;  // Use List for multiple results
import java.util.Optional;

@Repository
public interface FooterRepository extends JpaRepository<Footer, Long> {

    // Query to find a single footer by institutecode
    @Query("SELECT f FROM Footer f WHERE f.institutecode = :institutecode")
    Optional<Footer> findByInstitutecode(@Param("institutecode") String institutecode);

    // Query to find all footers by institutecode
    @Query("SELECT f FROM Footer f WHERE f.institutecode = :institutecode")
    List<Footer> findAllByInstitutecode(@Param("institutecode") String institutecode);  // Updated to List

    // Method to check if footer exists by institutecode
    boolean existsByInstitutecode(String institutecode);

}
