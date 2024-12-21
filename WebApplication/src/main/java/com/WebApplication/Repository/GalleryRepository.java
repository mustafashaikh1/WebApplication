package com.WebApplication.Repository;

import com.WebApplication.Entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    List<Gallery> findByInstitutecode(String institutecode);

}
