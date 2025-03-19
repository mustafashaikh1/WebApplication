package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.WebHRDetails;
import com.WebApplication.Repository.WebHRDetailsRepository;
import com.WebApplication.Service.WebHRDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebHRDetailsServiceImpl implements WebHRDetailsService {

    @Autowired
    private WebHRDetailsRepository webHRDetailsRepository;

    @Override
    public WebHRDetails createWebHRDetails(WebHRDetails webHRDetails) {
        return webHRDetailsRepository.save(webHRDetails);
    }

    @Override
    public WebHRDetails updateWebHRDetails(Long id, WebHRDetails webHRDetails) {
        WebHRDetails existingHRDetails = webHRDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WebHRDetails not found with id: " + id));

        existingHRDetails.setHrName(webHRDetails.getHrName());
        existingHRDetails.setEmail(webHRDetails.getEmail());
        existingHRDetails.setContact(webHRDetails.getContact());

        return webHRDetailsRepository.save(existingHRDetails);
    }

    @Override
    public void deleteWebHRDetails(Long id) {
        webHRDetailsRepository.deleteById(id);
    }

    @Override
    public Optional<WebHRDetails> getWebHRDetailsById(Long id) {
        return webHRDetailsRepository.findById(id);
    }

    @Override
    public List<WebHRDetails> getAllWebHRDetails() {
        return webHRDetailsRepository.findAll();
    }

    @Override
    public Optional<WebHRDetails> getHRDetailsByJobCareerOption(Long jobCareerOptionId) {
        return webHRDetailsRepository.findByJobCareerOption_Id(jobCareerOptionId);
    }
}
