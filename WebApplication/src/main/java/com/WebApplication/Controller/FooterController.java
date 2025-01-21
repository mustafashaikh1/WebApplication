package com.WebApplication.Controller;

import com.WebApplication.Entity.Footer;
import com.WebApplication.Service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class FooterController {

    @Autowired
    private FooterService footerService;

    @PostMapping("/createFooter")
    public ResponseEntity<?> createFooter(@RequestParam String title,
                                          @RequestParam String footerColor,
                                          @RequestParam String institutecode,
                                          @RequestParam String email,
                                          @RequestParam String mobileNumber,
                                          @RequestParam String address,
                                          @RequestParam(required = false) String instagramLink,
                                          @RequestParam(required = false) String facebookLink,
                                          @RequestParam(required = false) String twitterLink,
                                          @RequestParam(required = false) String youtubeLink,
                                          @RequestParam(required = false) String whatsappLink) {
        try {
            Footer footer = new Footer();
            footer.setTitle(title);
            footer.setFooterColor(footerColor);
            footer.setEmail(email);
            footer.setMobileNumber(mobileNumber);
            footer.setAddress(address);

            // Set social media links if provided
            footer.setInstagramLink(instagramLink);
            footer.setFacebookLink(facebookLink);
            footer.setTwitterLink(twitterLink);
            footer.setYoutubeLink(youtubeLink);
            footer.setWhatsappLink(whatsappLink);

            // Save the footer using the service, which will handle validation and saving
            Footer createdFooter = footerService.saveFooter(footer, institutecode);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdFooter);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PutMapping("/updateFooter")
    public ResponseEntity<?> updateFooter(@RequestParam String title,
                                          @RequestParam String footerColor,
                                          @RequestParam String institutecode,
                                          @RequestParam String email,
                                          @RequestParam String mobileNumber,
                                          @RequestParam String address,
                                          @RequestParam(required = false) String instagramLink,
                                          @RequestParam(required = false) String facebookLink,
                                          @RequestParam(required = false) String twitterLink,
                                          @RequestParam(required = false) String youtubeLink,
                                          @RequestParam(required = false) String whatsappLink) {
        try {
            // Fetch the Footer entity based on institutecode
            Footer updatedFooter = new Footer();
            updatedFooter.setTitle(title);
            updatedFooter.setFooterColor(footerColor);
            updatedFooter.setEmail(email); // Set email
            updatedFooter.setMobileNumber(mobileNumber); // Set mobile number
            updatedFooter.setAddress(address); // Set address

            // Set social media links if provided
            updatedFooter.setInstagramLink(instagramLink);
            updatedFooter.setFacebookLink(facebookLink);
            updatedFooter.setTwitterLink(twitterLink);
            updatedFooter.setYoutubeLink(youtubeLink);
            updatedFooter.setWhatsappLink(whatsappLink);

            // Call the service to update the Footer entry
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Footer not found for institutecode: " + institutecode));
    }



}
