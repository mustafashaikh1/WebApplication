package com.WebApplication.Repository;

import com.WebApplication.Entity.Topper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopperRepository extends JpaRepository<Topper, Long> {

    Optional<Topper> findByInstitutecode(String institutecode);

    Optional<Topper> findByTopperIdAndInstitutecode(Long topperId, String institutecode);

    boolean existsByInstitutecode(String institutecode);
}
