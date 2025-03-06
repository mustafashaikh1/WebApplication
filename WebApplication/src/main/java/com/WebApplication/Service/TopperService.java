package com.WebApplication.Service;

import com.WebApplication.Entity.Topper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TopperService {
    Topper createTopper(Topper topper, String institutecode, List<MultipartFile> topperImages) throws IOException;
    Topper updateTopperByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode, List<MultipartFile> topperImages, String topperColor) throws IOException;
    void deleteTopper(Long id);
    Topper getTopperById(Long id);
    List<Topper> getAllToppers(String institutecode);
}
