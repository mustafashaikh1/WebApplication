package com.WebApplication.Repository;

import com.WebApplication.Entity.JobCareerOption;
import com.WebApplication.Entity.MapAndImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobCareerOptionRepository extends JpaRepository<JobCareerOption, Long> {



    Optional<List<JobCareerOption>> getJobCareerOptionsByInstitutecode(String institutecode);


    List<JobCareerOption> findByInstitutecode(String institutecode);
}
