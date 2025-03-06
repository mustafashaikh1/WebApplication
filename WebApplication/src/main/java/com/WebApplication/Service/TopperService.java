package com.WebApplication.Service;

import com.WebApplication.Entity.Topper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TopperService {

    Topper createTopper(Topper topper, String institutecode, List<MultipartFile> topperImages) throws IOException;

    boolean existsByInstitutecode(String institutecode);

    void deleteTopperByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode);
    void deleteTopperByInstitutecode(String institutecode);

    Topper updateTopperByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode, List<MultipartFile> topperImages, String topperColor) throws IOException;

    Optional<Topper> getAllToppersByInstitutecode(String institutecode);
}
