package com.WebApplication.Service;

import com.WebApplication.Entity.AboutUs;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AboutUsService {

    AboutUs createAboutUs(AboutUs aboutUs, String institutecode, MultipartFile aboutUsImage);
    AboutUs updateAboutUs(Long id, AboutUs aboutUs, MultipartFile aboutUsImage);
    void deleteAboutUs(Long id);
    AboutUs getAboutUsById(Long id);
    Optional<AboutUs> getAllAboutUs(String institutecode);
}
