package com.WebApplication.Service;

import com.WebApplication.Entity.Footer;
import java.util.Optional;

public interface FooterService {
    Footer saveFooter(Footer footer, String institutecode);
    Footer updateFooter(String institutecode, Footer updatedFooter);
    void deleteFooter(String institutecode);
    Optional<Footer> getFooterByInstitutecode(String institutecode);

    // Individual update operations
    Footer updateInstagram(String institutecode,  String link);
    Footer updateFacebook(String institutecode,  String link);
    Footer updateTwitter(String institutecode,  String link);
    Footer updateYouTube(String institutecode,  String link);
    Footer updateWhatsApp(String institutecode, String link);
    // Individual post operations
    Footer postInstagram(String institutecode,  String link);
    Footer postFacebook(String institutecode, String link);
    Footer postTwitter(String institutecode,  String link);
    Footer postYouTube(String institutecode,  String link);
    Footer postWhatsApp(String institutecode, String link);

    // Individual delete operations
    void deleteInstagram(String institutecode);
    void deleteFacebook(String institutecode);
    void deleteTwitter(String institutecode);
    void deleteYouTube(String institutecode);
    void deleteWhatsApp(String institutecode);

    boolean existsByInstitutecode(String institutecode);
}
