package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Footer;
import com.WebApplication.Repository.FooterRepository;
import com.WebApplication.Service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FooterServiceImpl implements FooterService {

    @Autowired
    private FooterRepository footerRepository;

    @Override
    public Footer saveFooter(Footer footer, String institutecode) {
        if (existsByInstitutecode(institutecode)) {
            throw new RuntimeException("A Footer entry with institutecode '" + institutecode + "' already exists.");
        }
        footer.setInstitutecode(institutecode);
        return footerRepository.save(footer);
    }

    @Override
    public Footer updateFooter(String institutecode, Footer updatedFooter) {
        Footer existingFooter = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Footer not found with institutecode: " + institutecode));

        // Update the existing footer with new details (including individual social icons)
        existingFooter.setInstagramIcon(updatedFooter.getInstagramIcon());
        existingFooter.setFacebookIcon(updatedFooter.getFacebookIcon());
        existingFooter.setTwitterIcon(updatedFooter.getTwitterIcon());
        existingFooter.setYoutubeIcon(updatedFooter.getYoutubeIcon());

        existingFooter.setTitle(updatedFooter.getTitle());
        existingFooter.setFooterColor(updatedFooter.getFooterColor());
        existingFooter.setInstagramLink(updatedFooter.getInstagramLink());
        existingFooter.setFacebookLink(updatedFooter.getFacebookLink());
        existingFooter.setTwitterLink(updatedFooter.getTwitterLink());
        existingFooter.setYoutubeLink(updatedFooter.getYoutubeLink());

        // Save the updated footer
        return footerRepository.save(existingFooter);
    }

    @Override
    public void deleteFooter(String institutecode) {
        Footer footer = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Footer not found with institutecode: " + institutecode));
        footerRepository.delete(footer);
    }

    @Override
    public Optional<Footer> getFooterByInstitutecode(String institutecode) {
        return footerRepository.findByInstitutecode(institutecode);
    }

    @Override
    public boolean existsByInstitutecode(String institutecode) {
        return footerRepository.existsByInstitutecode(institutecode);
    }


    @Override
    public Footer updateInstagram(String institutecode, String icon, String link) {
        return updateField(institutecode, icon, link, "Instagram");
    }

    @Override
    public Footer updateFacebook(String institutecode, String icon, String link) {
        return updateField(institutecode, icon, link, "Facebook");
    }

    @Override
    public Footer updateTwitter(String institutecode, String icon, String link) {
        return updateField(institutecode, icon, link, "Twitter");
    }

    @Override
    public Footer updateYouTube(String institutecode, String icon, String link) {
        return updateField(institutecode, icon, link, "YouTube");
    }

    @Override
    public Footer postInstagram(String institutecode, String icon, String link) {
        return postField(institutecode, icon, link, "Instagram");
    }

    @Override
    public Footer postFacebook(String institutecode, String icon, String link) {
        return postField(institutecode, icon, link, "Facebook");
    }

    @Override
    public Footer postTwitter(String institutecode, String icon, String link) {
        return postField(institutecode, icon, link, "Twitter");
    }

    @Override
    public Footer postYouTube(String institutecode, String icon, String link) {
        return postField(institutecode, icon, link, "YouTube");
    }

    @Override
    public void deleteInstagram(String institutecode) {
        deleteField(institutecode, "Instagram");
    }

    @Override
    public void deleteFacebook(String institutecode) {
        deleteField(institutecode, "Facebook");
    }

    @Override
    public void deleteTwitter(String institutecode) {
        deleteField(institutecode, "Twitter");
    }

    @Override
    public void deleteYouTube(String institutecode) {
        deleteField(institutecode, "YouTube");
    }


    private Footer updateField(String institutecode, String icon, String link, String field) {
        Footer footer = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Footer not found with institutecode: " + institutecode));

        switch (field) {
            case "Instagram":
                footer.setInstagramIcon(icon);
                footer.setInstagramLink(link);
                break;
            case "Facebook":
                footer.setFacebookIcon(icon);
                footer.setFacebookLink(link);
                break;
            case "Twitter":
                footer.setTwitterIcon(icon);
                footer.setTwitterLink(link);
                break;
            case "YouTube":
                footer.setYoutubeIcon(icon);
                footer.setYoutubeLink(link);
                break;
        }

        return footerRepository.save(footer);
    }

    private Footer postField(String institutecode, String icon, String link, String field) {
        Footer footer = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Footer not found with institutecode: " + institutecode));

        // Assume post operation is same as update for now
        return updateField(institutecode, icon, link, field);
    }

    private void deleteField(String institutecode, String field) {
        Footer footer = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Footer not found with institutecode: " + institutecode));

        switch (field) {
            case "Instagram":
                footer.setInstagramIcon(null);
                footer.setInstagramLink(null);
                break;
            case "Facebook":
                footer.setFacebookIcon(null);
                footer.setFacebookLink(null);
                break;
            case "Twitter":
                footer.setTwitterIcon(null);
                footer.setTwitterLink(null);
                break;
            case "YouTube":
                footer.setYoutubeIcon(null);
                footer.setYoutubeLink(null);
                break;
        }

        footerRepository.save(footer);
    }
}
