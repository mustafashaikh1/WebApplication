package com.WebApplication.Controller;

import com.WebApplication.Entity.UrlMapping;
import com.WebApplication.Service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "https://pjsofttech.in")
public class UrlController {

    @Autowired
    private UrlMappingService urlMappingService;

    // Create
    @PostMapping("/{dynamicPart}")
    public String addDynamicUrl(@PathVariable String dynamicPart, @RequestParam String institutecode) {
        urlMappingService.createUrlMapping(dynamicPart, institutecode);
        return "Dynamic part '" + dynamicPart + "' with institute code '" + institutecode + "' has been successfully added.";
    }

    // Read
    @GetMapping("/{dynamicPart}")
    public ResponseEntity<?> handleDynamicUrl(@PathVariable String dynamicPart, @RequestParam String institutecode) {
        UrlMapping urlMapping = urlMappingService.getUrlMapping(dynamicPart, institutecode);
        if (urlMapping == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message", "Dynamic URL part '" + dynamicPart + "' does not exist for institute code '" + institutecode + "'."
                    ));
        }
        return ResponseEntity.ok(Map.of(
                "id", urlMapping.getId(),
                "dynamicPart", urlMapping.getDynamicPart(),
                "institutecode", urlMapping.getInstitutecode()
        ));
    }




    // Read All
    @GetMapping("/getAllMappings")
    public List<UrlMapping> getAllMappings(@RequestParam String institutecode) {
        return urlMappingService.getAllUrlMappings(institutecode);
    }

    // Update
    @PutMapping("/updateUrlMapping/{id}")
    public String updateUrlMapping(@PathVariable Long id, @RequestParam String dynamicPart) {
        UrlMapping updatedMapping = urlMappingService.updateUrlMapping(id, dynamicPart);
        return "URL Mapping updated successfully. ID: " + updatedMapping.getId() + ", Dynamic Part: " + updatedMapping.getDynamicPart();
    }

    // Delete
    @DeleteMapping("/deleteUrlMapping/{id}")
    public String deleteUrlMapping(@PathVariable Long id) {
        urlMappingService.deleteUrlMapping(id);
        return "URL Mapping with ID " + id + " has been successfully deleted.";
    }
}
