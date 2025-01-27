package com.WebApplication.Service;

import com.WebApplication.Entity.SlideBar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SlideBarService {
     SlideBar createSlideBar(SlideBar slideBar, String institutecode, List<MultipartFile> slideImages) throws IOException;

    boolean existsByInstitutecode(String institutecode);

    void deleteSlideBarByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode);


//    List<SlideBar> getAllSlideBars();


//    SlideBar updateSlideBarById(Long id, SlideBar updatedSlideBar, List<MultipartFile> slideImages) throws IOException;

    SlideBar updateSlideBarByImageUrlIdAndInstitutecode(Long imageUrlId, String institutecode, List<MultipartFile> slideImages, String slideBarColor) throws IOException;

    Optional<SlideBar> getAllSlideBarsByInstitutecode(String institutecode);


}
