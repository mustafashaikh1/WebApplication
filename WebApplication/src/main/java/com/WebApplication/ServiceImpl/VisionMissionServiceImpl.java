package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.VisionMission;
import com.WebApplication.Repository.VisionMissionRepository;
import com.WebApplication.Service.S3Service;
import com.WebApplication.Service.VisionMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class VisionMissionServiceImpl implements VisionMissionService {

    @Autowired
    private VisionMissionRepository visionMissionRepository;

    @Autowired
    private S3Service s3Service;

    @Override
    public VisionMission saveVisionMission(VisionMission visionMission, MultipartFile directorImage, String institutecode) throws IOException {
        if (existsByInstitutecode(institutecode)) {
            throw new RuntimeException("A Vision and Mission entry with institutecode '" + institutecode + "' already exists.");
        }
        visionMission.setInstitutecode(institutecode);

        if (directorImage != null && !directorImage.isEmpty()) {
            String imageUrl = s3Service.uploadImage(directorImage);
            visionMission.setDirectorImage(imageUrl);
        }

        return visionMissionRepository.save(visionMission);
    }

    @Override
    public VisionMission updateVisionMission(String institutecode, VisionMission updatedVisionMission, MultipartFile directorImage) throws IOException {
        VisionMission existingVisionMission = visionMissionRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Vision and Mission not found with institutecode: " + institutecode));

        if (updatedVisionMission.getVision() != null) existingVisionMission.setVision(updatedVisionMission.getVision());
        if (updatedVisionMission.getMission() != null) existingVisionMission.setMission(updatedVisionMission.getMission());
        if (updatedVisionMission.getVisionmissionColor() != null) existingVisionMission.setVisionmissionColor(updatedVisionMission.getVisionmissionColor());
        if (updatedVisionMission.getDirectorMessage() != null) existingVisionMission.setDirectorMessage(updatedVisionMission.getDirectorMessage());
        if (updatedVisionMission.getDirectorName() != null) existingVisionMission.setDirectorName(updatedVisionMission.getDirectorName());
        if (updatedVisionMission.getDescription() != null) existingVisionMission.setDescription(updatedVisionMission.getDescription());

        if (directorImage != null && !directorImage.isEmpty()) {
            String imageUrl = s3Service.uploadImage(directorImage);
            existingVisionMission.setDirectorImage(imageUrl);
        }

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
