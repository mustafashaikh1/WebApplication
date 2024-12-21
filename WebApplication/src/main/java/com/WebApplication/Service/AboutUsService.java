package com.WebApplication.Service;

import com.WebApplication.Entity.AboutUs;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AboutUsService {

    // Method to create AboutUs with image upload
    AboutUs createAboutUs(AboutUs aboutUs, String institutecode, MultipartFile aboutUsImage);

    // Method to update AboutUs with image upload
    AboutUs updateAboutUs(Long id, AboutUs aboutUs, MultipartFile aboutUsImage);

    void deleteAboutUs(Long id);

    AboutUs getAboutUsById(Long id);

    List<AboutUs> getAllAboutUs(String institutecode);
}
