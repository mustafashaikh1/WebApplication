package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Gallery;
import com.WebApplication.Repository.GalleryRepository;
import com.WebApplication.Service.GalleryService;
import com.WebApplication.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GalleryServiceImpl implements GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private S3Service s3Service;

    @Override
    public Gallery createGallery(Gallery gallery, String institutecode, List<MultipartFile> galleryImages) throws IOException {
        if (galleryRepository.existsByInstitutecodeAndEventNameAndYear(institutecode, gallery.getEventName(), gallery.getYear())) {
            throw new RuntimeException("Gallery with this event name and year already exists for this institution.");
        }

        if (gallery.getGalleryImages() == null) {
            gallery.setGalleryImages(new ArrayList<>());
        }
        if (gallery.getImageUrlIds() == null) {
            gallery.setImageUrlIds(new ArrayList<>());
        }

        int imageUrlIdCounter = 1;

        if (galleryImages != null && !galleryImages.isEmpty()) {
            for (MultipartFile galleryImage : galleryImages) {
                String imageUrl = s3Service.uploadImage(galleryImage);
                gallery.getGalleryImages().add(imageUrl);
                gallery.getImageUrlIds().add(imageUrlIdCounter++);
            }
        }

        return galleryRepository.save(gallery);
    }

    @Override
    public Gallery updateGalleryByGalleryIdAndInstitutecode(Long galleryId, String institutecode, List<MultipartFile> galleryImages, String galleryColor) throws IOException {
        Optional<Gallery> optionalGallery = galleryRepository.findByGalleryIdAndInstitutecode(galleryId, institutecode);

        if (optionalGallery.isPresent()) {
            Gallery gallery = optionalGallery.get();

            // If new images are provided, upload and append them to the existing list
            if (galleryImages != null && !galleryImages.isEmpty()) {
                int imageIdCounter = gallery.getImageUrlIds().size() + 1; // Start from next ID
                for (MultipartFile galleryImage : galleryImages) {
                    String newImageUrl = s3Service.uploadImage(galleryImage);
                    gallery.getGalleryImages().add(newImageUrl);  // ✅ Append the new image
                    gallery.getImageUrlIds().add(imageIdCounter++);  // ✅ Assign new ID
                }
            }

            // Update galleryColor if provided
            if (galleryColor != null && !galleryColor.isEmpty()) {
                gallery.setGalleryColor(galleryColor);
            }

            return galleryRepository.save(gallery);
        } else {
            throw new RuntimeException("Gallery not found with galleryId: " + galleryId + " and institutecode: " + institutecode);
        }
    }


    @Override
    public void deleteGalleryByGalleryIdAndInstitutecode(Long galleryId, String institutecode) {
        Optional<Gallery> optionalGallery = galleryRepository.findByGalleryIdAndInstitutecode(galleryId, institutecode);

        if (optionalGallery.isPresent()) {
            galleryRepository.delete(optionalGallery.get());
        } else {
            throw new RuntimeException("Gallery not found with galleryId: " + galleryId + " and institutecode: " + institutecode);
        }
    }

    @Override
    public Optional<Gallery> getGalleryByInstitutecode(String institutecode) {
        return Optional.ofNullable(galleryRepository.findByInstitutecode(institutecode).stream().findFirst().orElse(null));
    }
}
