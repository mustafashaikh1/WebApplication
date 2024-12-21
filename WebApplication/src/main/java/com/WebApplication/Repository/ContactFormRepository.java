package com.WebApplication.Repository;

import com.WebApplication.Entity.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
    List<ContactForm> findByInstitutecode(String institutecode);

}
