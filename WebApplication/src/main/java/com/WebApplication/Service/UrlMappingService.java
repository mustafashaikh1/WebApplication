package com.WebApplication.Service;

import com.WebApplication.Entity.UrlMapping;

import java.util.List;

public interface UrlMappingService {
    void createUrlMapping(String dynamicPart, String institutecode); // Create
    UrlMapping getUrlMapping(String dynamicPart, String institutecode); // Read
    List<UrlMapping> getAllUrlMappings(String institutecode); // Read All
    UrlMapping updateUrlMapping(Long id, String dynamicPart); // Update (removed institutecode)
    void deleteUrlMapping(Long id); // Delete
}
