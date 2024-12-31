package com.WebApplication.Service;

import com.WebApplication.Entity.Footer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FooterService {

    // Save a footer for a specific institutecode (if not already present)
    Footer saveFooter(Footer footer, String institutecode, MultipartFile footerImage) throws IOException;

    // Check if a footer exists for the given institutecode
    boolean existsByInstitutecode(String institutecode);

    // Delete the footer for a specific institutecode
    void deleteFooter(String institutecode);

    // Get the footer by institutecode
    Optional<Footer> getFooterByInstitutecode(String institutecode);

    // Update the footer for a specific institutecode
    Footer updateFooterByInstitutecode(String institutecode, Footer updatedFooter, MultipartFile footerImage) throws IOException;

    Footer addOrUpdateSocialLink(String institutecode, String socialLinkName, String link);
    String getSocialLink(String institutecode, String socialLinkName);
    boolean deleteSocialLink(String institutecode, String socialLinkName);


//
//    Optional<List<Footer>> getAllFooters(String institutecode);
}
