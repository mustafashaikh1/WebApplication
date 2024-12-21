package com.WebApplication.Controller;

import com.WebApplication.Entity.ManuBar;
import com.WebApplication.Service.ManuBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manuBars")
public class ManuBarController {

    @Autowired
    private ManuBarService manuBarService;

    @PostMapping("/createManuBar")
    public ResponseEntity<ManuBar> createManuBar(@RequestBody ManuBar manuBar,
                                                 @RequestParam String institutecode) {
        return ResponseEntity.ok(manuBarService.createManuBar(manuBar, institutecode));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManuBar> updateManuBar(@PathVariable Long id,
                                                 @RequestBody ManuBar manuBar) {
        ManuBar updatedManuBar = manuBarService.updateManuBar(id, manuBar);


        ManuBar responseManuBar = new ManuBar();
        responseManuBar.setManuBarId(updatedManuBar.getManuBarId());
        responseManuBar.setManuBarColor(updatedManuBar.getManuBarColor());


        return ResponseEntity.ok(responseManuBar);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteManuBar(@PathVariable Long id) {
        manuBarService.deleteManuBar(id);
        return ResponseEntity.ok("ManuBar deleted successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManuBar> getManuBarById(@PathVariable Long id) {
        return ResponseEntity.ok(manuBarService.getManuBarById(id));
    }

    @GetMapping("/getAllManuBars")
    public ResponseEntity<List<ManuBar>> getAllManuBars(@RequestParam String institutecode) {
        return ResponseEntity.ok(manuBarService.getAllManuBars(institutecode));
    }
}
