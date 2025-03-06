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

    @Query("SELECT ts FROM Testimonials ts WHERE :imageUrlId MEMBER OF ts.imageUrlIds AND ts.institutecode = :institutecode")
    Optional<Testimonials> findByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode);

    Optional<Testimonials> findByInstitutecode(String institutecode);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM testimonial_image_ids WHERE image_url_id = :imageUrlId AND testimonial_id IN (SELECT id FROM web_testimonial_section WHERE institutecode = :institutecode)", nativeQuery = true)
    void deleteTestimonialByImageUrlIdAndInstitutecode(@Param("imageUrlId") Long imageUrlId, @Param("institutecode") String institutecode);
}
