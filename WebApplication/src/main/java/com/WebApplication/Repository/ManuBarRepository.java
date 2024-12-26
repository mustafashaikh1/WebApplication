package com.WebApplication.Repository;

import com.WebApplication.Entity.ManuBar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManuBarRepository extends JpaRepository<ManuBar, Long> {

    @Query("SELECT m FROM ManuBar m WHERE m.institutecode = :institutecode")
    Optional<ManuBar> findByInstitutecode(@Param("institutecode") String institutecode);



    boolean existsByInstitutecode(String institutecode);



}
