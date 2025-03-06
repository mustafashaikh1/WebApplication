package com.WebApplication.Repository;

import com.WebApplication.Entity.Topper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopperRepository extends JpaRepository<Topper, Long> {

    boolean existsByInstitutecode(String institutecode);

    @Query("SELECT t FROM Topper t WHERE :imageUrlId MEMBER OF t.imageUrlIds AND t.institutecode = :institutecode")
    Optional<Topper> findByImageUrlIdAndInstitutecode(@Param("imageUrlId") Long imageUrlId, @Param("institutecode") String institutecode);

    Optional<Topper> findByInstitutecode(String institutecode);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM topper_image_ids WHERE image_url_id = :imageUrlId AND topper_id IN (SELECT topper_id FROM web_topper WHERE institutecode = :institutecode)", nativeQuery = true)
    void deleteTopperByImageUrlIdAndInstitutecode(@Param("imageUrlId") Long imageUrlId, @Param("institutecode") String institutecode);
}
