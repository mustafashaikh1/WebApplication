package com.WebApplication.Service;

import com.WebApplication.Entity.Footer;
import java.util.Optional;

public interface FooterService {
    Footer saveFooter(Footer footer, String institutecode);
    Footer updateFooter(String institutecode, Footer updatedFooter);
    void deleteFooter(String institutecode);
    Optional<Footer> getFooterByInstitutecode(String institutecode);

    // Individual update operations
    Footer updateInstagram(String institutecode, String icon, String link);
    Footer updateFacebook(String institutecode, String icon, String link);
    Footer updateTwitter(String institutecode, String icon, String link);
    Footer updateYouTube(String institutecode, String icon, String link);

    // Individual post operations
    Footer postInstagram(String institutecode, String icon, String link);
    Footer postFacebook(String institutecode, String icon, String link);
    Footer postTwitter(String institutecode, String icon, String link);
    Footer postYouTube(String institutecode, String icon, String link);

    // Individual delete operations
    void deleteInstagram(String institutecode);
    void deleteFacebook(String institutecode);
    void deleteTwitter(String institutecode);
    void deleteYouTube(String institutecode);

    boolean existsByInstitutecode(String institutecode);
}
