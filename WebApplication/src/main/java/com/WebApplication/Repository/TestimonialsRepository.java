package com.WebApplication.Repository;

import com.WebApplication.Entity.Testimonials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TestimonialsRepository extends JpaRepository<Testimonials, Long> {
    List<Testimonials> findByInstitutecode(String institutecode);  // Custom query to filter by institutecode

}
