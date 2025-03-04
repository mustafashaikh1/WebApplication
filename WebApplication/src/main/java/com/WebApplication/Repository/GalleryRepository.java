package com.WebApplication.Repository;

import com.WebApplication.Entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<Gallery> findByInstitutecode(String institutecode);

    boolean existsByInstitutecodeAndEventNameAndYear(String institutecode, String eventName, Integer year);

    Optional<Gallery> findByGalleryIdAndInstitutecode(Long galleryId, String institutecode);
}
