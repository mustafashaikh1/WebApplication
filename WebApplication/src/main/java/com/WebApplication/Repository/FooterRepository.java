package com.WebApplication.Repository;

import com.WebApplication.Entity.Footer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FooterRepository extends JpaRepository<Footer, Long> {

    List<Footer> findByInstitutecode(String institutecode); // Custom query to filter by institutecode
}
