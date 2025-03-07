package com.WebApplication.Repository;

import com.WebApplication.Entity.Testimonials;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TestimonialsRepository extends JpaRepository<Testimonials, Long> {
    boolean existsByInstitutecode(String institutecode);

    List<Testimonials> findAllByInstitutecode(String institutecode);


}
