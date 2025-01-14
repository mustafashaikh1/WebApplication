package com.WebApplication.Repository;



import com.WebApplication.Entity.AwardsAndAccolades;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardsAndAccoladesRepository extends JpaRepository<AwardsAndAccolades, Long> {
    // Additional query methods if needed
}
