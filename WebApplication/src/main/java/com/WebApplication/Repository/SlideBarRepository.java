package com.WebApplication.Repository;

import com.WebApplication.Entity.SlideBar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SlideBarRepository extends JpaRepository<SlideBar, Long> {
    boolean existsByInstitutecode(String institutecode);

    @Query("SELECT sb FROM SlideBar sb WHERE :imageUrlId MEMBER OF sb.imageUrlIds AND sb.institutecode = :institutecode")
    Optional<SlideBar> findByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode);

    Optional<SlideBar> findByInstitutecode(String institutecode);



}
