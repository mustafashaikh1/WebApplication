package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Topper;
import com.WebApplication.Repository.TopperRepository;
import com.WebApplication.Service.TopperService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TopperServiceImpl implements TopperService {

    @Autowired
    private TopperRepository topperRepository;


    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Topper createTopper(Topper topper, String institutecode, MultipartFile topperImage) throws IOException {
        topper.setInstitutecode(institutecode);

        if (topperImage != null && !topperImage.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(topperImage.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");
            topper.setTopperImage(imageUrl);
        }

        return topperRepository.save(topper);
    }

    @Override
    public Topper updateTopper(Long id, Topper topper, MultipartFile topperImage) throws IOException {
        Optional<Topper> existingTopper = topperRepository.findById(id);
        if (existingTopper.isPresent()) {
            Topper updatedTopper = existingTopper.get();
            updatedTopper.setName(topper.getName());
            updatedTopper.setTotalMarks(topper.getTotalMarks());
            updatedTopper.setPost(topper.getPost());
            updatedTopper.setRank(topper.getRank());
            updatedTopper.setYear(topper.getYear());

            if (topperImage != null && !topperImage.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(topperImage.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("secure_url");
                updatedTopper.setTopperImage(imageUrl);
            }

            return topperRepository.save(updatedTopper);
        } else {
            throw new RuntimeException("Topper not found with id: " + id);
        }
    }

    @Override
    public void deleteTopper(Long id) {
        topperRepository.deleteById(id);
    }

    @Override
    public Topper getTopperById(Long id) {
        return topperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topper not found with id: " + id));
    }

    @Override
    public List<Topper> getAllToppers(String institutecode) {
        return topperRepository.findByInstitutecode(institutecode);
    }
}
