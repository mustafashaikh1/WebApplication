// MapAndImagesServiceImpl.java
package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.MapAndImages;
import com.WebApplication.Repository.MapAndImagesRepository;
import com.WebApplication.Service.MapAndImagesService;
import com.WebApplication.Service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MapAndImagesServiceImpl implements MapAndImagesService {

    @Autowired
    private MapAndImagesRepository mapAndImagesRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public MapAndImages createMapAndImages(MapAndImages mapAndImages, String institutecode, MultipartFile contactImage) throws IOException {
        if (existsByInstitutecode(institutecode)) {
            throw new IllegalArgumentException("MapAndImages already exists for institutecode: " + institutecode);
        }
        mapAndImages.setInstitutecode(institutecode);
        String imageUrl = cloudinaryService.uploadImage(contactImage);
        mapAndImages.setContactImage(imageUrl);
        return mapAndImagesRepository.save(mapAndImages);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return mapAndImagesRepository.existsByInstitutecode(institutecode);
    }

    @Override
    public void deleteMapAndImages(String institutecode) {
        mapAndImagesRepository.deleteByInstitutecode(institutecode);
    }

    @Override
    public List<MapAndImages> getAllMapAndImages() {
        return mapAndImagesRepository.findAll();
    }

    @Override
    public MapAndImages updateMapAndImagesByInstitutecode(String institutecode, MapAndImages mapAndImages, MultipartFile contactImage) throws IOException {
        Optional<MapAndImages> existingMapAndImages = mapAndImagesRepository.findByInstitutecode(institutecode);
        if (existingMapAndImages.isPresent()) {
            MapAndImages existing = existingMapAndImages.get();
            existing.setMaps(mapAndImages.getMaps());
            if (contactImage != null && !contactImage.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(contactImage);
                existing.setContactImage(imageUrl);
            }
            return mapAndImagesRepository.save(existing);
        } else {
            throw new IllegalArgumentException("No MapAndImages found for institutecode: " + institutecode);
        }
    }

    @Override
    public Optional<MapAndImages> getMapAndImagesByInstitutecode(String institutecode) {
        return mapAndImagesRepository.findByInstitutecode(institutecode);
    }
}