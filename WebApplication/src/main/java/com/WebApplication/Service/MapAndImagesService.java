package com.WebApplication.Service;

import com.WebApplication.Entity.Course;
import com.WebApplication.Entity.MapAndImages;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MapAndImagesService {


    MapAndImages createMapAndImages(MapAndImages mapAndImages ,String institutecode, MultipartFile contactImage) throws IOException;


    boolean existsByInstitutecode(String institutecode);


    void deleteMapAndImages(String institutecode);


    List<MapAndImages> getAllMapAndImages();


    MapAndImages updateMapAndImagesByInstitutecode(String institutecode,MapAndImages mapAndImages,MultipartFile contactImage ) throws IOException;

    Optional<MapAndImages> getMapAndImagesByInstitutecode(String institutecode);

}
