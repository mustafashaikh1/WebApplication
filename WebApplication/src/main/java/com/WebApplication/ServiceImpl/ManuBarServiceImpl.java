package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.ManuBar;
import com.WebApplication.Repository.ManuBarRepository;
import com.WebApplication.Service.ManuBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManuBarServiceImpl implements ManuBarService {



    @Autowired
    private ManuBarRepository manuBarRepository;

    @Override
    public ManuBar createManuBar(ManuBar manuBar, String institutecode) {
        manuBar.setInstitutecode(institutecode);
        return manuBarRepository.save(manuBar);
    }

    @Override
    public ManuBar updateManuBar(Long id, ManuBar manuBar) {
        Optional<ManuBar> existingManuBar = manuBarRepository.findById(id);
        if (existingManuBar.isPresent()) {
            ManuBar updatedManuBar = existingManuBar.get();
            updatedManuBar.setManuBarColor(manuBar.getManuBarColor());


            return manuBarRepository.save(updatedManuBar);
        } else {
            throw new RuntimeException("ManuBar not found with id: " + id);
        }
    }


    @Override
    public void deleteManuBar(Long id) {
        manuBarRepository.deleteById(id);
    }

    @Override
    public ManuBar getManuBarById(Long id) {
        return manuBarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ManuBar not found with id: " + id));
    }

    @Override
    public List<ManuBar> getAllManuBars(String institutecode) {
        return manuBarRepository.findByInstitutecode(institutecode);
    }
}
