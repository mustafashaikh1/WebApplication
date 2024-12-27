package com.WebApplication.Repository;

import com.WebApplication.Entity.SlideBar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlideBarRepository extends JpaRepository<SlideBar, Long> {
    boolean existsByInstitutecode(String institutecode);

    Optional<SlideBar> findByInstitutecode(String institutecode);
}
