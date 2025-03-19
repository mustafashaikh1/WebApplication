package com.WebApplication.Repository;

import com.WebApplication.Entity.FacilityTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityTitleRepository extends JpaRepository<FacilityTitle, Long> {
}