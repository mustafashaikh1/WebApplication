package com.WebApplication.Service;

import com.WebApplication.Entity.ManuBar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ManuBarService {
    ManuBar createManuBar(ManuBar manuBar, String institutecode, MultipartFile aboutUsImage);

    boolean existsByInstitutecode(String institutecode);

    void deleteManuBar(Long id);

    List<ManuBar> getAllManuBars();


    ManuBar updateManuBarByInstitutecode(String institutecode, ManuBar updatedManuBar, MultipartFile menubarImage) throws IOException;

    Optional<ManuBar> getManuBarByInstitutecode(String institutecode);


}
