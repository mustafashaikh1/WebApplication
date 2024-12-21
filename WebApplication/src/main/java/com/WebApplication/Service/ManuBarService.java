package com.WebApplication.Service;

import com.WebApplication.Entity.ManuBar;
import java.util.List;

public interface ManuBarService {
    ManuBar createManuBar(ManuBar manuBar, String institutecode);
    ManuBar updateManuBar(Long id, ManuBar manuBar);
    void deleteManuBar(Long id);
    ManuBar getManuBarById(Long id);
    List<ManuBar> getAllManuBars(String institutecode);
}
