package com.WebApplication.Repository;

import com.WebApplication.Entity.VisionMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VisionMissionRepository extends JpaRepository<VisionMission,Long> {

    @Query("SELECT f FROM VisionMission f WHERE f.institutecode = :institutecode")
    Optional<VisionMission> findByInstitutecode(@Param("institutecode") String institutecode);

    boolean existsByInstitutecode(String institutecode);
}
