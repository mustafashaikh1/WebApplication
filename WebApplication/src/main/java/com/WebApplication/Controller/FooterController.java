package com.WebApplication.Controller;

import com.WebApplication.Entity.Footer;
import com.WebApplication.Service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/footer")
public class FooterController {

    @Autowired
    private FooterService footerService;

    // Get all footer details by institutecode
    @GetMapping
    public List<Footer> getAllFooterDetails(@RequestParam String institutecode) {
        return footerService.getAllFooterDetails(institutecode);
    }

    // Create a new footer detail
    @PostMapping
    public ResponseEntity<Footer> createFooterDetail(@RequestBody Footer footer, @RequestParam String institutecode) {
        Footer createdFooter = footerService.saveFooterDetail(footer, institutecode);
        return ResponseEntity.ok(createdFooter);
    }

    // Get a footer detail by ID
    @GetMapping("/{id}")
    public ResponseEntity<Footer> getFooterById(@PathVariable Long id) {
        Footer footer = footerService.getFooterById(id);
        return footer != null ? ResponseEntity.ok(footer) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Footer> updateFooterDetail(@PathVariable Long id,
                                                     @RequestBody Footer footer) {
        Footer updatedFooter = footerService.updateFooterDetail(id, footer);
        return updatedFooter != null ? ResponseEntity.ok(updatedFooter) : ResponseEntity.notFound().build();
    }


    // Delete a footer detail by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFooterDetail(@PathVariable Long id) {
        footerService.deleteFooterDetail(id);
        return ResponseEntity.ok("Footer deleted successfully.");
    }
}
