package com.WebApplication.Controller;

import com.WebApplication.Entity.UrlMapping;
import com.WebApplication.Exception.UrlMappingException;
import com.WebApplication.Service.UrlMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/urls")
@CrossOrigin(origins = {
        "https://pjsofttech.in",
        "https://live.ooacademy.co.in",
        "https://course.yashodapublication.com"
})
@Slf4j
public class UrlController {

    private final UrlMappingService urlMappingService;

    @Autowired
    public UrlController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    // Modified PostMapping to receive dynamicPart in request body
    @PostMapping
    public ResponseEntity<?> addDynamicUrl(@RequestBody Map<String, String> request) {
        String dynamicPart = request.get("dynamicPart");
        String institutecode = request.get("institutecode");

        log.info("Received request to add dynamic URL: {} for institute: {}", dynamicPart, institutecode);

        try {
            if (institutecode == null || institutecode.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Institute code is required"));
            }

            UrlMapping mapping = urlMappingService.createUrlMapping(dynamicPart.trim(), institutecode.trim());
            log.info("Successfully created URL mapping for: {}", dynamicPart);

            return ResponseEntity.ok(createSuccessResponse("URL mapping created successfully", mapping));
        } catch (UrlMappingException e) {
            log.error("Failed to create URL mapping: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error while creating URL mapping", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error occurred"));
        }
    }

    // Modified GetMapping to receive dynamicPart in request body
    @GetMapping
    public ResponseEntity<?> handleDynamicUrl(@RequestBody Map<String, String> request) {
        String dynamicPart = request.get("dynamicPart");
        String institutecode = request.get("institutecode");

        log.info("Fetching URL mapping for dynamic part: {} and institute: {}", dynamicPart, institutecode);

        try {
            if (institutecode == null || institutecode.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Institute code is required"));
            }

            UrlMapping mapping = urlMappingService.getUrlMapping(dynamicPart.trim(), institutecode.trim());
            if (mapping == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("No mapping found for the specified parameters"));
            }

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", mapping.getId());
            responseData.put("dynamicPart", mapping.getDynamicPart());
            responseData.put("institutecode", mapping.getInstitutecode());

            return ResponseEntity.ok(createSuccessResponse("URL mapping retrieved successfully", responseData));
        } catch (UrlMappingException e) {
            log.error("Failed to retrieve URL mapping: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error while retrieving URL mapping", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error occurred"));
        }
    }

    @GetMapping("/mappings")
    public ResponseEntity<?> getAllMappings(@RequestBody Map<String, String> request) {
        String institutecode = request.get("institutecode");

        log.info("Fetching all mappings for institute: {}", institutecode);
        try {
            if (institutecode == null || institutecode.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Institute code is required"));
            }

            List<UrlMapping> mappings = urlMappingService.getAllUrlMappings(institutecode.trim());
            return ResponseEntity.ok(createSuccessResponse("URL mappings retrieved successfully", mappings));
        } catch (UrlMappingException e) {
            log.error("Failed to retrieve URL mappings: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error while retrieving URL mappings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error occurred"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUrlMapping(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String dynamicPart = request.get("dynamicPart");

        log.info("Updating URL mapping with ID: {} to new dynamic part: {}", id, dynamicPart);
        try {
            if (dynamicPart == null || dynamicPart.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Dynamic part is required"));
            }

            UrlMapping updatedMapping = urlMappingService.updateUrlMapping(id, dynamicPart.trim());
            return ResponseEntity.ok(createSuccessResponse("URL mapping updated successfully", updatedMapping));
        } catch (UrlMappingException e) {
            log.error("Failed to update URL mapping: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error while updating URL mapping", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error occurred"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUrlMapping(@PathVariable Long id) {
        log.info("Deleting URL mapping with ID: {}", id);
        try {
            urlMappingService.deleteUrlMapping(id);
            return ResponseEntity.ok(createSuccessResponse("URL mapping deleted successfully", null));
        } catch (UrlMappingException e) {
            log.error("Failed to delete URL mapping: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error while deleting URL mapping", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error occurred"));
        }
    }

    @GetMapping("/getInstitutecodeByDynamicPart/{dynamicPart}")
    public ResponseEntity<?>getInstitutecodeByDynamicPart(@PathVariable String dynamicPart) {
        log.info("Fetching institute code for dynamic part: {}", dynamicPart);
        try {
            String institutecode = urlMappingService.getInstitutecodeByDynamicPart(dynamicPart.trim());
            return ResponseEntity.ok(createSuccessResponse("Institute code retrieved successfully",
                    Map.of("institutecode", institutecode)));
        } catch (UrlMappingException e) {
            log.error("Failed to retrieve institute code: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error while retrieving institute code", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error occurred"));
        }
    }

    private Map<String, Object> createSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        if (data != null) {
            response.put("data", data);
        }
        return response;
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", message);
        return response;
    }
}
