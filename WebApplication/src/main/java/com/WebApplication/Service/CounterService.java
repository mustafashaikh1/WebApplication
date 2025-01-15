package com.WebApplication.Service;



import com.WebApplication.Entity.Counter;
import java.util.List;

public interface CounterService {

    Counter createCounter(Counter counter, String institutecode);
    List<Counter> getAllCounters();
    Counter getCounterById(Long id);

    Counter updateCounter(Long id, Counter counter);
    void deleteCounter(Long id);

    List<Counter> getCountersByInstitutecode(String institutecode);
}
