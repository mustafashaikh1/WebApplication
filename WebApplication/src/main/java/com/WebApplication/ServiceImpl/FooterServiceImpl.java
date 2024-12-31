package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Footer;
import com.WebApplication.Repository.FooterRepository;
import com.WebApplication.Service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FooterServiceImpl implements FooterService {

    @Autowired
    private FooterRepository footerRepository;

    @Override
    public Footer saveFooter(Footer footer, String institutecode, MultipartFile footerImage) throws IOException {
        // Check if footer already exists for the given institutecode
        if (existsByInstitutecode(institutecode)) {
            throw new IllegalArgumentException("Footer already exists for this institutecode.");
        }
        footer.setInstitutecode(institutecode);
        // You can add logic here for saving the footerImage if needed
        // footer.setFooterImage(footerImage);
        return footerRepository.save(footer);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return footerRepository.findByInstitutecode(institutecode).isPresent();
    }

    @Override
    public void deleteFooter(String institutecode) {
        Footer footer = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new IllegalArgumentException("Footer not found for this institutecode."));
        footerRepository.delete(footer);
    }

    @Override
    public Optional<Footer> getFooterByInstitutecode(String institutecode) {
        return footerRepository.findByInstitutecode(institutecode);
    }

//    @Override
//    public Optional<List<Footer>> getAllFooters(String institutecode) {
//        List<Footer> footers = footerRepository.findByInstitutecode(institutecode);
//        return Optional.ofNullable(footers); // Wrap the list in Optional
//    }


    @Override
    public Footer updateFooterByInstitutecode(String institutecode, Footer updatedFooter, MultipartFile footerImage) throws IOException {
        Footer existingFooter = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new IllegalArgumentException("Footer not found for this institutecode."));

        // Update footer fields
        existingFooter.setSocialIcon(updatedFooter.getSocialIcon());
        existingFooter.setInstagramLink(updatedFooter.getInstagramLink());
        existingFooter.setFacebookLink(updatedFooter.getFacebookLink());
        existingFooter.setTwitterLink(updatedFooter.getTwitterLink());
        existingFooter.setYoutubeLink(updatedFooter.getYoutubeLink());
        existingFooter.setTitle(updatedFooter.getTitle());
        existingFooter.setFooterColor(updatedFooter.getFooterColor());
        // You can add logic here for updating the footerImage if needed

        return footerRepository.save(existingFooter);
    }

    @Override
    public Footer addOrUpdateSocialLink(String institutecode, String socialLinkName, String link) {
        Footer footer = footerRepository.findByInstitutecode(institutecode)
                .orElse(new Footer(institutecode));

        switch (socialLinkName) {
            case "instagramLink":
                footer.setInstagramLink(link);
                break;
            case "facebookLink":
                footer.setFacebookLink(link);
                break;
            case "twitterLink":
                footer.setTwitterLink(link);
                break;
            case "youtubeLink":
                footer.setYoutubeLink(link);
                break;
            default:
                return null;  // Invalid link name
        }

        return footerRepository.save(footer);
    }

    @Override
    public String getSocialLink(String institutecode, String socialLinkName) {
        Footer footer = footerRepository.findByInstitutecode(institutecode).orElse(null);
        if (footer == null) {
            return null;  // Footer not found
        }

        switch (socialLinkName) {
            case "instagramLink":
                return footer.getInstagramLink();
            case "facebookLink":
                return footer.getFacebookLink();
            case "twitterLink":
                return footer.getTwitterLink();
            case "youtubeLink":
                return footer.getYoutubeLink();
            default:
                return null;  // Invalid link name
        }
    }

    @Override
    public boolean deleteSocialLink(String institutecode, String socialLinkName) {
        Footer footer = footerRepository.findByInstitutecode(institutecode).orElse(null);
        if (footer == null) {
            return false;  // Footer not found
        }

        switch (socialLinkName) {
            case "instagramLink":
                footer.setInstagramLink(null);
                break;
            case "facebookLink":
                footer.setFacebookLink(null);
                break;
            case "twitterLink":
                footer.setTwitterLink(null);
                break;
            case "youtubeLink":
                footer.setYoutubeLink(null);
                break;
            default:
                return false;  // Invalid link name
        }

        footerRepository.save(footer);  // Save the footer after the deletion
        return true;
    }
}
