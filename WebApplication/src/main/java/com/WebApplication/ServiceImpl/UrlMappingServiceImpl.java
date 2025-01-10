package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.UrlMapping;
import com.WebApplication.Repository.UrlMappingRepository;
import com.WebApplication.Service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrlMappingServiceImpl implements UrlMappingService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Override
    public void createUrlMapping(String dynamicPart, String institutecode) {
        // Check if the dynamicPart is already associated with any institutecode
        if (urlMappingRepository.findByDynamicPart(dynamicPart).isPresent()) {
            throw new IllegalArgumentException("Dynamic URL part '" + dynamicPart + "' is already associated with another institute code!");
        }

        // Check if the institutecode is already associated with any dynamicPart
        if (urlMappingRepository.findByInstitutecode(institutecode).isPresent()) {
            throw new IllegalArgumentException("Institute code '" + institutecode + "' is already associated with another dynamic part!");
        }

        // Create and save a new UrlMapping
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setDynamicPart(dynamicPart);
        urlMapping.setInstitutecode(institutecode);
        urlMappingRepository.save(urlMapping);
    }

    @Override
    public UrlMapping getUrlMapping(String dynamicPart, String institutecode) {
        return urlMappingRepository.findByDynamicPartAndInstitutecode(dynamicPart, institutecode)
                .orElseThrow(() -> new IllegalArgumentException("No mapping found for dynamic part '" + dynamicPart +
                        "' and institute code '" + institutecode + "'!"));
    }

    @Override
    public List<UrlMapping> getAllUrlMappings(String institutecode) {
        return urlMappingRepository.findAllByInstitutecode(institutecode);
    }

    @Override
    public UrlMapping updateUrlMapping(Long id, String dynamicPart) {
        // Fetch the existing UrlMapping
        UrlMapping urlMapping = urlMappingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("URL Mapping with ID " + id + " does not exist!"));

        // Check if the new dynamicPart is already associated with another institute code
        if (urlMappingRepository.findByDynamicPart(dynamicPart).isPresent() &&
                !urlMapping.getDynamicPart().equals(dynamicPart)) {
            throw new IllegalArgumentException("Dynamic URL part '" + dynamicPart + "' is already associated with another institute code!");
        }

        // Update the dynamic part and save
        urlMapping.setDynamicPart(dynamicPart);
        return urlMappingRepository.save(urlMapping);
    }

    @Override
    public void deleteUrlMapping(Long id) {
        if (!urlMappingRepository.existsById(id)) {
            throw new IllegalArgumentException("URL Mapping with ID " + id + " does not exist!");
        }
        urlMappingRepository.deleteById(id);
    }

    @Override
    public String getInstitutecodeByDynamicPart(String dynamicPart) {
        return urlMappingRepository.findByDynamicPart(dynamicPart)
                .map(urlMapping -> urlMapping.getInstitutecode())
                .orElseThrow(() -> new IllegalArgumentException("No institute code found for dynamic part '" + dynamicPart + "'"));
    }
}
