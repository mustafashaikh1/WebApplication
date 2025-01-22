package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.UrlMapping;
import com.WebApplication.Exception.UrlMappingException;
import com.WebApplication.Repository.UrlMappingRepository;
import com.WebApplication.Service.UrlMappingService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UrlMappingServiceImpl implements UrlMappingService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    private static final int MAX_ATTEMPTS = 3;
    private static final long BACKOFF_DELAY = 1000L; // 1 second delay

    @Override
    @Transactional
    public UrlMapping createUrlMapping(String dynamicPart, String institutecode) throws UrlMappingException {
        return retry(() -> {
            validateInputs(dynamicPart, institutecode);
            checkExistingMappings(dynamicPart, institutecode);

            UrlMapping urlMapping = new UrlMapping();
            urlMapping.setDynamicPart(dynamicPart.trim());
            urlMapping.setInstitutecode(institutecode.trim());

            UrlMapping savedMapping = urlMappingRepository.save(urlMapping);
            log.info("Created URL mapping: {}", savedMapping);
            return savedMapping;
        });
    }

    @Override
    @Transactional
    public UrlMapping getUrlMapping(String dynamicPart, String institutecode) throws UrlMappingException {
        return retry(() -> {
            validateInputs(dynamicPart, institutecode);
            return urlMappingRepository.findByDynamicPartAndInstitutecode(dynamicPart.trim(), institutecode.trim())
                    .orElseThrow(() -> new UrlMappingException("No mapping found for given parameters"));
        });
    }

    @Override
    @Transactional
    public List<UrlMapping> getAllUrlMappings(String institutecode) throws UrlMappingException {
        return retry(() -> {
            if (institutecode == null || institutecode.trim().isEmpty()) {
                throw new IllegalArgumentException("Institute code cannot be empty");
            }
            return urlMappingRepository.findAllByInstitutecode(institutecode.trim());
        });
    }

    @Override
    @Transactional
    public UrlMapping updateUrlMapping(Long id, String dynamicPart) throws UrlMappingException {
        return retry(() -> {
            if (dynamicPart == null || dynamicPart.trim().isEmpty()) {
                throw new IllegalArgumentException("Dynamic part cannot be empty");
            }

            UrlMapping urlMapping = urlMappingRepository.findById(id)
                    .orElseThrow(() -> new UrlMappingException("URL Mapping not found with ID: " + id));

            Optional<UrlMapping> existingMapping = urlMappingRepository.findByDynamicPart(dynamicPart.trim());
            if (existingMapping.isPresent() && !existingMapping.get().getId().equals(id)) {
                throw new UrlMappingException("Dynamic part already exists: " + dynamicPart);
            }

            urlMapping.setDynamicPart(dynamicPart.trim());
            UrlMapping updatedMapping = urlMappingRepository.save(urlMapping);
            log.info("Updated URL mapping: {}", updatedMapping);
            return updatedMapping;
        });
    }

    @Override
    @Transactional
    public void deleteUrlMapping(Long id) throws UrlMappingException {
        retry(() -> {
            if (!urlMappingRepository.existsById(id)) {
                throw new UrlMappingException("URL Mapping not found with ID: " + id);
            }
            urlMappingRepository.deleteById(id);
            log.info("Deleted URL mapping with ID: {}", id);
            return null;
        });
    }

    @Override
    @Transactional
    public String getInstitutecodeByDynamicPart(String dynamicPart) throws UrlMappingException {
        return retry(() -> {
            if (dynamicPart == null || dynamicPart.trim().isEmpty()) {
                throw new IllegalArgumentException("Dynamic part cannot be empty");
            }
            return urlMappingRepository.findByDynamicPart(dynamicPart.trim())
                    .map(UrlMapping::getInstitutecode)
                    .orElseThrow(() -> new UrlMappingException("No institute code found for dynamic part: " + dynamicPart));
        });
    }

    private void validateInputs(String dynamicPart, String institutecode) {
        if (dynamicPart == null || dynamicPart.trim().isEmpty()) {
            throw new IllegalArgumentException("Dynamic part cannot be empty");
        }
        if (institutecode == null || institutecode.trim().isEmpty()) {
            throw new IllegalArgumentException("Institute code cannot be empty");
        }
    }

    private void checkExistingMappings(String dynamicPart, String institutecode) throws UrlMappingException {
        urlMappingRepository.findByDynamicPart(dynamicPart.trim()).ifPresent(mapping -> {
            throw new UrlMappingException("Dynamic part already exists: " + dynamicPart);
        });

        urlMappingRepository.findByInstitutecode(institutecode.trim()).ifPresent(mapping -> {
            throw new UrlMappingException("Institute code already exists: " + institutecode);
        });
    }

    // Retry utility method
    private <T> T retry(RetryableAction<T> action) throws UrlMappingException {
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            try {
                return action.run();
            } catch (DataAccessException e) {
                attempts++;
                if (attempts >= MAX_ATTEMPTS) {
                    log.error("Failed after {} attempts", attempts);
                    throw new UrlMappingException("Database access error: " + e.getMessage(), e);
                }
                try {
                    Thread.sleep(BACKOFF_DELAY);  // Backoff delay
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new UrlMappingException("Failed to perform operation after retry attempts");
    }

    @FunctionalInterface
    public interface RetryableAction<T> {
        T run() throws DataAccessException;
    }
}