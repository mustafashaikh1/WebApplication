package com.WebApplication.Repository;

import com.WebApplication.Entity.SlideBar;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SlideBarRepository extends JpaRepository<SlideBar, Long> {
    boolean existsByInstitutecode(String institutecode);

    @Query("SELECT sb FROM SlideBar sb WHERE :imageUrlId MEMBER OF sb.imageUrlIds AND sb.institutecode = :institutecode")
    Optional<SlideBar> findByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode);

    Optional<SlideBar> findByInstitutecode(String institutecode);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM image_url_ids WHERE image_url_id = :imageUrlId AND slide_id IN (SELECT id FROM web_slide_bar WHERE institutecode = :institutecode)", nativeQuery = true)
    void deleteSlideBarByImageUrlIdAndInstitutecode(@Param("imageUrlId") Long imageUrlId, @Param("institutecode") String institutecode);



}
