package com.WebApplication.Controller;

import com.WebApplication.Entity.UrlMapping;
import com.WebApplication.Service.UrlMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class UrlController {

    @Autowired
    private UrlMappingService urlMappingService;

    @PostMapping("/{dynamicPart}")
    public String addDynamicUrl(@PathVariable String dynamicPart, @RequestParam String institutecode) {
        if (institutecode == null || institutecode.isEmpty()) {
            throw new RuntimeException("Institutecode is required.");
        }
        urlMappingService.createUrlMapping(dynamicPart, institutecode);
        return "Dynamic part '" + dynamicPart + "' has been successfully added for institute code '" + institutecode + "'.";
    }

    @GetMapping("/{dynamicPart}")
    public ResponseEntity<?> handleDynamicUrl(@PathVariable String dynamicPart, @RequestParam String institutecode) {
        if (institutecode == null || institutecode.isEmpty()) {
            throw new RuntimeException("Institutecode is required.");
        }
        UrlMapping urlMapping = urlMappingService.getUrlMapping(dynamicPart, institutecode);
        if (urlMapping == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Dynamic URL part '" + dynamicPart + "' does not exist for institute code '" + institutecode + "'."));
        }
        return ResponseEntity.ok(Map.of(
                "id", urlMapping.getId(),
                "dynamicPart", urlMapping.getDynamicPart(),
                "institutecode", urlMapping.getInstitutecode()
        ));
    }

    @GetMapping("/getAllMappings")
    public List<UrlMapping> getAllMappings(@RequestParam String institutecode) {
        if (institutecode == null || institutecode.isEmpty()) {
            throw new RuntimeException("Institutecode is required.");
        }
        return urlMappingService.getAllUrlMappings(institutecode);
    }

    @PutMapping("/updateUrlMapping/{id}")
    public String updateUrlMapping(@PathVariable Long id, @RequestParam String dynamicPart) {
        UrlMapping updatedMapping = urlMappingService.updateUrlMapping(id, dynamicPart);
        return "URL Mapping updated successfully. ID: " + updatedMapping.getId() + ", Dynamic Part: " + updatedMapping.getDynamicPart();
    }

    @DeleteMapping("/deleteUrlMapping/{id}")
    public String deleteUrlMapping(@PathVariable Long id) {
        urlMappingService.deleteUrlMapping(id);
        return "URL Mapping with ID " + id + " has been successfully deleted.";
    }

    @GetMapping("/getInstitutecodeByDynamicPart/{dynamicPart}")
    public ResponseEntity<?> getInstitutecodeByDynamicPart(@PathVariable String dynamicPart) {
        String institutecode = urlMappingService.getInstitutecodeByDynamicPart(dynamicPart);
        if (institutecode == null) {
            throw new RuntimeException("No institutecode found for the provided dynamic part.");
        }
        return ResponseEntity.ok(institutecode);
    }
}
