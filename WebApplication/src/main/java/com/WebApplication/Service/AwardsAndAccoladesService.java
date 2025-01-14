package com.WebApplication.Service;

import com.WebApplication.Entity.AwardsAndAccolades;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AwardsAndAccoladesService {
    AwardsAndAccolades saveAward(AwardsAndAccolades award, String institutecode, MultipartFile awardImage) throws IOException;
    AwardsAndAccolades updateAward(Long id, AwardsAndAccolades award, MultipartFile awardImage) throws IOException;
    void deleteAward(Long id);
    AwardsAndAccolades getAwardById(Long id);
    List<AwardsAndAccolades> getAllAwards(String institutecode);
}
