package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.VisionMission;
import com.WebApplication.Repository.VisionMissionRepository;

import com.WebApplication.Service.VisionMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VisionMissionServiceImpl implements VisionMissionService {

    @Autowired
    private VisionMissionRepository visionMissionRepository;

    @Override
    public VisionMission saveVisionMission(VisionMission visionMission, String institutecode) {
        if (existsByInstitutecode(institutecode)) {
            throw new RuntimeException("A Vision and Mission entry with institutecode '" + institutecode + "' already exists.");
        }
        visionMission.setInstitutecode(institutecode);
        return visionMissionRepository.save(visionMission);
    }

    @Override
    public VisionMission updateVisionMission(String institutecode, VisionMission updatedVisionMission) {
        VisionMission existingVisionMission = visionMissionRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Vision and Mission not found with institutecode: " + institutecode));

        existingVisionMission.setVision(updatedVisionMission.getVision());
        existingVisionMission.setMission(updatedVisionMission.getMission());
        existingVisionMission.setVisionmissionColor(updatedVisionMission.getVisionmissionColor());

        return visionMissionRepository.save(existingVisionMission);
    }

    @Override
    public void deleteVisionMission(String institutecode) {
        VisionMission visionMission = visionMissionRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Vision and Mission not found with institutecode: " + institutecode));
        visionMissionRepository.delete(visionMission);
    }

    @Override
    public Optional<VisionMission> getVisionMissionByInstitutecode(String institutecode) {
        return visionMissionRepository.findByInstitutecode(institutecode);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return visionMissionRepository.existsByInstitutecode(institutecode);
    }
}
