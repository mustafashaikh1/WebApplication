package com.WebApplication.Repository;

import com.WebApplication.Entity.Footer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FooterRepository extends JpaRepository<Footer, Long> {

    @Query("SELECT f FROM Footer f WHERE f.institutecode = :institutecode")
    Optional<Footer> findByInstitutecode(@Param("institutecode") String institutecode);

    boolean existsByInstitutecode(String institutecode);
}
