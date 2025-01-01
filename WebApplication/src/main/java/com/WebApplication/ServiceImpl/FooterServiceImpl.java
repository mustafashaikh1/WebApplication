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
}
