package com.WebApplication.Controller;

import com.WebApplication.Entity.Footer;
import com.WebApplication.Service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class FooterController {

    @Autowired
    private FooterService footerService;

    @PostMapping("/createFooter")
    public ResponseEntity<?> createFooter(@RequestParam String instagramIcon,
                                          @RequestParam String facebookIcon,
                                          @RequestParam String twitterIcon,
                                          @RequestParam String youtubeIcon,
                                          @RequestParam String title,
                                          @RequestParam String footerColor,
                                          @RequestParam String instagramLink,
                                          @RequestParam String facebookLink,
                                          @RequestParam String twitterLink,
                                          @RequestParam String youtubeLink,
                                          @RequestParam String institutecode) {
        try {
            if (footerService.existsByInstitutecode(institutecode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A Footer with the given institutecode already exists.");
            }

            Footer footer = new Footer();
            footer.setInstagramIcon(instagramIcon);
            footer.setFacebookIcon(facebookIcon);
            footer.setTwitterIcon(twitterIcon);
            footer.setYoutubeIcon(youtubeIcon);
            footer.setTitle(title);
            footer.setFooterColor(footerColor);
            footer.setInstagramLink(instagramLink);
            footer.setFacebookLink(facebookLink);
            footer.setTwitterLink(twitterLink);
            footer.setYoutubeLink(youtubeLink);
            footer.setInstitutecode(institutecode);

            Footer createdFooter = footerService.saveFooter(footer, institutecode);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFooter);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateFooter")
    public ResponseEntity<?> updateFooter(@RequestParam String institutecode,
                                          @RequestParam String instagramIcon,
                                          @RequestParam String facebookIcon,
                                          @RequestParam String twitterIcon,
                                          @RequestParam String youtubeIcon,
                                          @RequestParam String title,
                                          @RequestParam String footerColor,
                                          @RequestParam String instagramLink,
                                          @RequestParam String facebookLink,
                                          @RequestParam String twitterLink,
                                          @RequestParam String youtubeLink) {
        try {
            Footer updatedFooter = new Footer();
            updatedFooter.setInstagramIcon(instagramIcon);
            updatedFooter.setFacebookIcon(facebookIcon);
            updatedFooter.setTwitterIcon(twitterIcon);
            updatedFooter.setYoutubeIcon(youtubeIcon);
            updatedFooter.setTitle(title);
            updatedFooter.setFooterColor(footerColor);
            updatedFooter.setInstagramLink(instagramLink);
            updatedFooter.setFacebookLink(facebookLink);
            updatedFooter.setTwitterLink(twitterLink);
            updatedFooter.setYoutubeLink(youtubeLink);

            Footer result = footerService.updateFooter(institutecode, updatedFooter);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteFooter")
    public ResponseEntity<String> deleteFooter(@RequestParam String institutecode) {
        try {
            footerService.deleteFooter(institutecode);
            return ResponseEntity.ok("Footer deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getFooterByInstitutecode")
    public ResponseEntity<Footer> getFooterByInstitutecode(@RequestParam String institutecode) {
        return footerService.getFooterByInstitutecode(institutecode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
