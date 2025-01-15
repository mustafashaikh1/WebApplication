package com.WebApplication.Repository;

import com.WebApplication.Entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findByInstitutecode(String institutecode);
}
