package com.WebApplication.Repository;

import com.WebApplication.Entity.Testimonials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TestimonialsRepository extends JpaRepository<Testimonials, Long> {

    @Query("SELECT t FROM Testimonials t WHERE t.institutecode = :institutecode")
    Optional<Testimonials> findByInstitutecode(@Param("institutecode") String institutecode);

    boolean existsByInstitutecode(String institutecode);
}
