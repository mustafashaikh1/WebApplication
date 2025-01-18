package com.WebApplication.Service;

import com.WebApplication.Entity.Footer;
import java.util.Optional;

public interface FooterService {
    Footer saveFooter(Footer footer, String institutecode);
    Footer updateFooter(String institutecode, Footer updatedFooter);
    void deleteFooter(String institutecode);
    Optional<Footer> getFooterByInstitutecode(String institutecode);


    boolean existsByInstitutecode(String institutecode);
}
