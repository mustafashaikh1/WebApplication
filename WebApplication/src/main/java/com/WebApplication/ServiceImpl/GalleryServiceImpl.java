package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Gallery;
import com.WebApplication.Repository.GalleryRepository;
import com.WebApplication.Service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GalleryServiceImpl implements GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;




    @Override
    public Gallery createGallery(Gallery gallery, String institutecode) {
        gallery.setInstitutecode(institutecode);  // Set institutecode when creating a gallery
        return galleryRepository.save(gallery);
    }

    @Override
    public Gallery updateGallery(Long id, Gallery gallery) {
        Optional<Gallery> existingGallery = galleryRepository.findById(id);
        if (existingGallery.isPresent()) {
            Gallery updatedGallery = existingGallery.get();
            updatedGallery.setEventName(gallery.getEventName());
            updatedGallery.setYear(gallery.getYear());
            updatedGallery.setGalleryImage(gallery.getGalleryImage()); // Ensure the image URL is updated

            return galleryRepository.save(updatedGallery);
        } else {
            throw new RuntimeException("Gallery not found with id: " + id);
        }
    }

    @Override
    public void deleteGallery(Long id) {
        galleryRepository.deleteById(id);
    }

    @Override
    public Gallery getGalleryById(Long id) {
        return galleryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + id));
    }

    @Override
    public List<Gallery> getAllGalleries(String institutecode) {
        return galleryRepository.findByInstitutecode(institutecode);  // Fetch galleries by institutecode
    }
}
