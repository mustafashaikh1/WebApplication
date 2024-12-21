package com.WebApplication.Repository;

import com.WebApplication.Entity.ManuBar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManuBarRepository extends JpaRepository<ManuBar, Long> {
    List<ManuBar> findByInstitutecode(String institutecode);
}
