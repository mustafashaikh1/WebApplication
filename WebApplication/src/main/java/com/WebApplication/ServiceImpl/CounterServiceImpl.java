package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Counter;
import com.WebApplication.Repository.CounterRepository;
import com.WebApplication.Service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterServiceImpl implements CounterService {

    private final CounterRepository counterRepository;

    @Autowired
    public CounterServiceImpl(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @Override
    public Counter createCounter(Counter counter, String institutecode) {
        counter.setInstitutecode(institutecode);
        return counterRepository.save(counter);
    }

    @Override
    public List<Counter> getAllCounters() {
        return counterRepository.findAll();
    }

    @Override
    public Counter getCounterById(Long id) {
        return counterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Counter not found with id " + id));
    }


    @Override
    public List<Counter> getCountersByInstitutecode(String institutecode) {
        return counterRepository.findByInstitutecode(institutecode);
    }
    @Override
    public Counter updateCounter(Long id, Counter counter) {
        Counter existingCounter = getCounterById(id);
        existingCounter.setCounterName1(counter.getCounterName1());
        existingCounter.setCountValue1(counter.getCountValue1());
        existingCounter.setCounterColor1(counter.getCounterColor1());
        existingCounter.setCounterName2(counter.getCounterName2());
        existingCounter.setCountValue2(counter.getCountValue2());
        existingCounter.setCounterColor2(counter.getCounterColor2());
        existingCounter.setCounterName3(counter.getCounterName3());
        existingCounter.setCountValue3(counter.getCountValue3());
        existingCounter.setCounterColor3(counter.getCounterColor3());
        existingCounter.setInstitutecode(counter.getInstitutecode());
        return counterRepository.save(existingCounter);
    }

    @Override
    public void deleteCounter(Long id) {
        counterRepository.deleteById(id);
    }
}
