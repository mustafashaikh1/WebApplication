package com.WebApplication.Service;



import com.WebApplication.Entity.VisionMission;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface VisionMissionService {

    VisionMission saveVisionMission(VisionMission visionMission, MultipartFile directorImage, String institutecode) throws IOException;

    boolean existsByInstitutecode(String institutecode);

    void deleteVisionMission(String institutecode);

    Optional<VisionMission> getVisionMissionByInstitutecode(String institutecode);

    VisionMission updateVisionMission(String institutecode, VisionMission updatedVisionMission, MultipartFile directorImage) throws IOException;
}
