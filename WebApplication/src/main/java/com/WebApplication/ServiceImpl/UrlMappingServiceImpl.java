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
        if (urlMappingRepository.existsByDynamicPart(dynamicPart)) {
            throw new IllegalArgumentException("Dynamic URL part already exists!");
        }
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setDynamicPart(dynamicPart);
        urlMapping.setInstitutecode(institutecode); // Set institute code
        urlMappingRepository.save(urlMapping);
    }

    @Override
    public UrlMapping getUrlMapping(String dynamicPart, String institutecode) {
        Optional<UrlMapping> optionalMapping = Optional.ofNullable(urlMappingRepository.findByDynamicPartAndInstitutecode(dynamicPart, institutecode));
        if (optionalMapping.isPresent()) {
            return optionalMapping.get();
        }
        // Return null or handle it appropriately
        return null;
    }



    @Override
    public List<UrlMapping> getAllUrlMappings(String institutecode) {
        return urlMappingRepository.findAllByInstitutecode(institutecode);
    }

    @Override
    public UrlMapping updateUrlMapping(Long id, String dynamicPart) {
        Optional<UrlMapping> optionalUrlMapping = urlMappingRepository.findById(id);
        if (optionalUrlMapping.isEmpty()) {
            throw new IllegalArgumentException("URL Mapping with ID " + id + " does not exist!");
        }
        UrlMapping urlMapping = optionalUrlMapping.get();
        urlMapping.setDynamicPart(dynamicPart); // Only update the dynamic part
        return urlMappingRepository.save(urlMapping);
    }

    @Override
    public void deleteUrlMapping(Long id) {
        if (!urlMappingRepository.existsById(id)) {
            throw new IllegalArgumentException("URL Mapping with ID " + id + " does not exist!");
        }
        urlMappingRepository.deleteById(id);
    }
}
