package com.WebApplication.Repository;

import com.WebApplication.Entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface    UrlMappingRepository extends JpaRepository<UrlMapping, Long> {


    @Query("SELECT u FROM UrlMapping u WHERE u.dynamicPart = :dynamicPart AND u.institutecode = :institutecode")
    Optional<UrlMapping> findByDynamicPartAndInstitutecode(@Param("dynamicPart") String dynamicPart, @Param("institutecode") String institutecode);

    @Query("SELECT u FROM UrlMapping u WHERE u.institutecode = :institutecode")
    Optional<UrlMapping> findByInstitutecode(@Param("institutecode") String institutecode);

    @Query("SELECT u FROM UrlMapping u WHERE u.dynamicPart = :dynamicPart")
    Optional<UrlMapping> findByDynamicPart(@Param("dynamicPart") String dynamicPart);

    @Query("SELECT u FROM UrlMapping u WHERE u.institutecode = :institutecode")
    List<UrlMapping> findAllByInstitutecode(@Param("institutecode") String institutecode);


}
