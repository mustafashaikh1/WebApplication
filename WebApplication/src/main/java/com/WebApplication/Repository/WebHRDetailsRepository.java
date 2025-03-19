package com.WebApplication.Repository;

import com.WebApplication.Entity.WebHRDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebHRDetailsRepository extends JpaRepository<WebHRDetails, Long> {
    Optional<WebHRDetails> findByJobCareerOption_Id(Long jobCareerOptionId);
}
