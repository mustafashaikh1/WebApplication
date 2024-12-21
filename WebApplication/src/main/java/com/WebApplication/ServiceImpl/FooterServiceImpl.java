package com.WebApplication.ServiceImpl;

import com.WebApplication.Entity.Footer;
import com.WebApplication.Repository.FooterRepository;
import com.WebApplication.Service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FooterServiceImpl implements FooterService {

    @Autowired
    private FooterRepository footerRepository;



    @Override
    public List<Footer> getAllFooterDetails(String institutecode) {
        return footerRepository.findByInstitutecode(institutecode); // Using institutecode filter
    }

    @Override
    public Footer saveFooterDetail(Footer footer, String institutecode) {
        footer.setInstitutecode(institutecode); // Set institutecode when saving
        return footerRepository.save(footer);
    }

    @Override
    public Footer getFooterById(Long id) {
        return footerRepository.findById(id).orElse(null);
    }

    @Override
    public Footer updateFooterDetail(Long id, Footer footer) {
        Footer existingFooter = footerRepository.findById(id).orElse(null);
        if (existingFooter == null) {
            return null;
        }

        // Keep the existing institutecode intact
        String existingInstitutecode = existingFooter.getInstitutecode();

        // Update the remaining fields
        existingFooter.setSocialIcon(footer.getSocialIcon());
        existingFooter.setLink(footer.getLink());
        existingFooter.setTitle(footer.getTitle());
        existingFooter.setFooterColor(footer.getFooterColor());

        // Ensure the institutecode remains unchanged
        existingFooter.setInstitutecode(existingInstitutecode);

        return footerRepository.save(existingFooter);
    }


    @Override
    public void deleteFooterDetail(Long id) {
        footerRepository.deleteById(id);
    }
}
