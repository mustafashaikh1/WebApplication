package com.WebApplication.Repository;

import com.WebApplication.Entity.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {

    @Query("SELECT c FROM ContactForm c WHERE c.institutecode = :institutecode")
    Optional<ContactForm> findByInstitutecode(@Param("institutecode") String institutecode);

    boolean existsByInstitutecode(String institutecode);

    


    @Query("SELECT c.contactImage FROM ContactForm c WHERE c.institutecode = :institutecode")
    Optional<String> findContactImageByInstitutecode(@Param("institutecode") String institutecode);

    @Query("SELECT c.maps FROM ContactForm c WHERE c.institutecode = :institutecode")
    Optional<String> findMapsByInstitutecode(@Param("institutecode") String institutecode);

    @Query("SELECT c FROM ContactForm c WHERE c.contactImage IS NOT NULL AND c.maps IS NOT NULL AND c.institutecode = :institutecode")
    Optional<ContactForm> findContactImageAndMapByInstitutecode(@Param("institutecode") String institutecode);

    @Query("SELECT c FROM ContactForm c WHERE c.contactImage IS NOT NULL AND c.maps IS NOT NULL")
    List<ContactForm> findAllWithContactImageAndMaps();


}