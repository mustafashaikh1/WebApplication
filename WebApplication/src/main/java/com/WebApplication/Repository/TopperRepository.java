package com.WebApplication.Repository;

import com.WebApplication.Entity.Topper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopperRepository extends JpaRepository<Topper, Long> {
    List<Topper> findByInstitutecode(String institutecode);

}
