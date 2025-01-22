package com.WebApplication.Repository;

import com.WebApplication.Entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    List<Gallery> findByInstitutecode(String institutecode);

}
