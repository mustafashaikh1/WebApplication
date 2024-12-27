package com.WebApplication.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    // Upload a single image to Cloudinary
    public String uploadImage(MultipartFile file) throws IOException {
        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url");
    }

    // Upload multiple images to Cloudinary
    public List<String> uploadImages(List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String imageUrl = uploadImage(file); // Use the uploadImage method for each file
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }
}
