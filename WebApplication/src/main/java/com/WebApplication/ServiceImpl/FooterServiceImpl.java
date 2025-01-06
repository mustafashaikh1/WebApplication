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

    //****************************************POST ICONS****************************************************//

    @Override
    public Footer postInstagram(String institutecode,  String link) {
        return postField(institutecode,  link, "Instagram");
    }

    @Override
    public Footer postFacebook(String institutecode,  String link) {
        return postField(institutecode,  link, "Facebook");
    }

    @Override
    public Footer postTwitter(String institutecode,  String link) {
        return postField(institutecode,  link, "Twitter");
    }

    @Override
    public Footer postYouTube(String institutecode,  String link) {
        return postField(institutecode,  link, "YouTube");
    }


    //****************************************UPDATE  ICONS****************************************************//

    @Override
    public Footer updateInstagram(String institutecode,  String link) {
        return updateField(institutecode,  link, "Instagram");
    }

    @Override
    public Footer updateFacebook(String institutecode,  String link) {
        return updateField(institutecode,  link, "Facebook");
    }

    @Override
    public Footer updateTwitter(String institutecode,  String link) {
        return updateField(institutecode, link, "Twitter");
    }

    @Override
    public Footer updateYouTube(String institutecode,  String link) {
        return updateField(institutecode,  link, "YouTube");
    }


    //****************************************DELETE ICONS****************************************************//
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


    private Footer updateField(String institutecode,  String link, String field) {
        Footer footer = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Footer not found with institutecode: " + institutecode));

        switch (field) {
            case "Instagram":

                footer.setInstagramLink(link);
                break;
            case "Facebook":

                footer.setFacebookLink(link);
                break;
            case "Twitter":

                footer.setTwitterLink(link);
                break;
            case "YouTube":

                footer.setYoutubeLink(link);
                break;
        }

        return footerRepository.save(footer);
    }

    private Footer postField(String institutecode,  String link, String field) {
        Footer footer = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Footer not found with institutecode: " + institutecode));

        // Assume post operation is same as update for now
        return updateField(institutecode,  link, field);
    }

    private void deleteField(String institutecode, String field) {
        Footer footer = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Footer not found with institutecode: " + institutecode));

        switch (field) {
            case "Instagram":

                footer.setInstagramLink(null);
                break;
            case "Facebook":

                footer.setFacebookLink(null);
                break;
            case "Twitter":

                footer.setTwitterLink(null);
                break;
            case "YouTube":

                footer.setYoutubeLink(null);
                break;
        }

        footerRepository.save(footer);
    }
}
