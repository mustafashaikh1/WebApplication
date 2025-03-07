package com.WebApplication.Service;

import com.WebApplication.Entity.Topper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TopperService {

    Topper createTopper(Topper topper, String institutecode, List<MultipartFile> topperImages) throws IOException;

    boolean existsByInstitutecode(String institutecode);

    void deleteTopperById(Long topperId);
    void deleteTopperByInstitutecode(String institutecode);

    Topper updateTopperById(Long topperId, List<MultipartFile> topperImages, String topperColor,
                            String name, Double totalMarks, String post, Integer rank, Integer year) throws IOException;
    Optional<Topper> getTopperById(Long topperId);

    List<Topper> getAllToppersByInstitutecode(String institutecode);
}
