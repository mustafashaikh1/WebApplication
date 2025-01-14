package com.WebApplication.Service;

import com.WebApplication.Entity.Gallery;
import java.util.List;
import java.util.Optional;

public interface GalleryService {
    Gallery createGallery(Gallery gallery,String institutecode);
    Gallery updateGallery(Long id, Gallery gallery);
    void deleteGallery(Long id);
    Gallery getGalleryById(Long id);
    List<Gallery> getAllGalleries(String institutecode);


}
