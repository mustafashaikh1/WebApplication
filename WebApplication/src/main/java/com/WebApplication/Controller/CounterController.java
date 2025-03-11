package com.WebApplication.Controller;

import com.WebApplication.Entity.Counter;
import com.WebApplication.Service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {
        "https://pjsofttech.in",
        "https://live.ooacademy.co.in",
        "https://course.yashodapublication.com",
        "https://lokrajyaacademy.com"
})
@RequestMapping("/api/counters")
public class CounterController {

    private final CounterService counterService;

    @Autowired
    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping
    public List<Counter> getAllCounters() {
        return counterService.getAllCounters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Counter> getCounterById(@PathVariable Long id) {
        return ResponseEntity.ok(counterService.getCounterById(id));
    }

    @GetMapping("/by-institutecode/{institutecode}")
    public ResponseEntity<List<Counter>> getCountersByInstitutecode(@PathVariable String institutecode) {
        List<Counter> counters = counterService.getCountersByInstitutecode(institutecode);
        return ResponseEntity.ok(counters);
    }


    @PostMapping
    public ResponseEntity<Counter> createCounter(@RequestBody Counter counter, @RequestParam String institutecode) {
        return new ResponseEntity<>(counterService.createCounter(counter, institutecode), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Counter> updateCounter(@PathVariable Long id, @RequestBody Counter counter) {
        return ResponseEntity.ok(counterService.updateCounter(id, counter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCounter(@PathVariable Long id) {
        counterService.deleteCounter(id);
        return ResponseEntity.noContent().build();
    }
}
