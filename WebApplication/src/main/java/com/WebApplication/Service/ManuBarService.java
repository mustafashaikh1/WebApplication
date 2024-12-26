package com.WebApplication.Service;

import com.WebApplication.Entity.ManuBar;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ManuBarService {
    ManuBar createManuBar(ManuBar manuBar, String institutecode, MultipartFile aboutUsImage);
    void deleteManuBar(Long id);

    List<ManuBar> getAllManuBars();


    ManuBar updateManuBarByInstitutecode(String institutecode, ManuBar manuBar);

    List<ManuBar> getManuBarByInstitutecode(String institutecode);


}
