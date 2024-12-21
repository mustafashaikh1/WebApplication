package com.WebApplication.Service;

import com.WebApplication.Entity.Footer;

import java.util.List;

public interface FooterService {
    List<Footer> getAllFooterDetails(String institutecode);
    Footer saveFooterDetail(Footer footer, String institutecode);
    Footer getFooterById(Long id);

    Footer updateFooterDetail(Long id, Footer footer);
    void deleteFooterDetail(Long id);
}


