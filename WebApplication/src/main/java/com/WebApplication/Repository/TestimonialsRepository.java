package com.WebApplication.Repository;

import com.WebApplication.Entity.Testimonials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestimonialsRepository extends JpaRepository<Testimonials, Long> {
    List<Testimonials> findByInstitutecode(String institutecode);  // Custom query to filter by institutecode

}
