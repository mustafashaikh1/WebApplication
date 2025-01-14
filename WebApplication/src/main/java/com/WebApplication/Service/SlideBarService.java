package com.WebApplication.Service;

import com.WebApplication.Entity.SlideBar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SlideBarService {
     SlideBar createSlideBar(SlideBar slideBar, String institutecode, List<MultipartFile> slideImages) throws IOException;

    boolean existsByInstitutecode(String institutecode);


    void deleteSlideBar(Long id);

    List<SlideBar> getAllSlideBars();

    SlideBar updateSlideBarByInstitutecode(String institutecode, SlideBar updatedSlideBar,List<MultipartFile> slideImages) throws IOException;

    Optional<SlideBar> getSlideBarByInstitutecode(String institutecode);


}
