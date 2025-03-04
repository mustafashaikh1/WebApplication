package com.WebApplication.Service;

import com.WebApplication.Entity.Gallery;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface GalleryService {
    Gallery createGallery(Gallery gallery, String institutecode, List<MultipartFile> galleryImages) throws IOException;

    Gallery updateGalleryByGalleryIdAndInstitutecode(Long galleryId, String institutecode, List<MultipartFile> galleryImages, String galleryColor) throws IOException;

    void deleteGalleryByGalleryIdAndInstitutecode(Long galleryId, String institutecode);

    Optional<Gallery> getGalleryByInstitutecode(String institutecode);


}
