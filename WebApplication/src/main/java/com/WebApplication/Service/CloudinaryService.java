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
            String imageUrl = uploadImage(file);
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    // Delete an image from Cloudinary
    public boolean deleteImage(String imageUrl) throws IOException {
        // Extract public ID from the image URL
        String publicId = extractPublicId(imageUrl);

        // Perform deletion
        Map<String, Object> deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return "ok".equals(deleteResult.get("result"));
    }

    // Extract public ID from the image URL
    private String extractPublicId(String imageUrl) {
        String[] parts = imageUrl.split("/");
        String publicIdWithExtension = parts[parts.length - 1]; // Get the last part of the URL
        return publicIdWithExtension.split("\\.")[0]; // Remove the file extension
    }
}
