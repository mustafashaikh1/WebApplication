package com.WebApplication.Service;



import com.WebApplication.Entity.VisionMission;

import java.util.Optional;

public interface VisionMissionService {

    VisionMission saveVisionMission(VisionMission visionMission, String institutecode);

    boolean existsByInstitutecode(String institutecode);

    void deleteVisionMission(String institutecode);

    Optional<VisionMission> getVisionMissionByInstitutecode(String institutecode);

    VisionMission updateVisionMission(String institutecode, VisionMission updatedVisionMission);
}
