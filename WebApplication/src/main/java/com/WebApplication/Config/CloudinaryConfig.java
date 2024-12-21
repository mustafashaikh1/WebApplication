package com.WebApplication.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dur99c6l7",
                "api_key", "529578716945785",
                "api_secret", "PjdPRqqzsFyMMrvyLxi2X5RPTsI"));
    }
}