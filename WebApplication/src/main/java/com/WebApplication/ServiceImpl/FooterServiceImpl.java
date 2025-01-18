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
        Footer footer = footerRepository.findByInstitutecode(institutecode)
                .orElseThrow(() -> new RuntimeException("Footer not found with institutecode: " + institutecode));

        // Update fields in the existing Footer
        footer.setTitle(updatedFooter.getTitle());
        footer.setFooterColor(updatedFooter.getFooterColor());
        footer.setEmail(updatedFooter.getEmail());
        footer.setMobileNumber(updatedFooter.getMobileNumber());
        footer.setAddress(updatedFooter.getAddress());
        footer.setInstagramLink(updatedFooter.getInstagramLink());
        footer.setFacebookLink(updatedFooter.getFacebookLink());
        footer.setTwitterLink(updatedFooter.getTwitterLink());
        footer.setYoutubeLink(updatedFooter.getYoutubeLink());
        footer.setWhatsappLink(updatedFooter.getWhatsappLink());

        return footerRepository.save(footer);
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
