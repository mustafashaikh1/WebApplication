package com.WebApplication.Repository;



import com.WebApplication.Entity.AwardsAndAccolades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardsAndAccoladesRepository extends JpaRepository<AwardsAndAccolades, Long> {
    // Additional query methods if needed
}
