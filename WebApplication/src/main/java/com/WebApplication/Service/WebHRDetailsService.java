package com.WebApplication.Service;

import com.WebApplication.Entity.WebHRDetails;

import java.util.List;
import java.util.Optional;

public interface WebHRDetailsService {
    WebHRDetails createWebHRDetails(WebHRDetails webHRDetails);
    WebHRDetails updateWebHRDetails(Long id, WebHRDetails webHRDetails);
    void deleteWebHRDetails(Long id);
    Optional<WebHRDetails> getWebHRDetailsById(Long id);
    List<WebHRDetails> getAllWebHRDetails();
    Optional<WebHRDetails> getHRDetailsByJobCareerOption(Long jobCareerOptionId);
}
