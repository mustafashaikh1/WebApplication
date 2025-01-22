package com.WebApplication.Repository;

import com.WebApplication.Entity.Topper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TopperRepository extends JpaRepository<Topper, Long> {
    List<Topper> findByInstitutecode(String institutecode);

}
