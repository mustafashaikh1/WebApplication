package com.WebApplication.Controller;

import com.WebApplication.Entity.Footer;
import com.WebApplication.Service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class FooterController {

    @Autowired
    private FooterService footerService;

    // Create a Footer for a specific institutecode
    @PostMapping("/createFooter")
    public ResponseEntity<?> createFooter(@RequestParam String institutecode,
                                          @RequestParam String socialIcon,
                                          @RequestParam String instagramLink,
                                          @RequestParam String facebookLink,
                                          @RequestParam String twitterLink,
                                          @RequestParam String youtubeLink,
                                          @RequestParam String title,
                                          @RequestParam String footerColor,
                                          @RequestParam(required = false) MultipartFile footerImage) {
        try {
            if (footerService.existsByInstitutecode(institutecode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A Footer with the given institutecode already exists.");
            }

            Footer footer = new Footer();
            footer.setInstitutecode(institutecode);
            footer.setSocialIcon(socialIcon);
            footer.setInstagramLink(instagramLink);
            footer.setFacebookLink(facebookLink);
            footer.setTwitterLink(twitterLink);
            footer.setYoutubeLink(youtubeLink);
            footer.setTitle(title);
            footer.setFooterColor(footerColor);

            Footer createdFooter = footerService.saveFooter(footer, institutecode, footerImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFooter);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload footer image: " + e.getMessage());
        }
    }

    // Update a Footer by institutecode
    @PutMapping("/updateFooterByInstitutecode")
    public ResponseEntity<?> updateFooterByInstitutecode(@RequestParam String institutecode,
                                                         @RequestParam String socialIcon,
                                                         @RequestParam String instagramLink,
                                                         @RequestParam String facebookLink,
                                                         @RequestParam String twitterLink,
                                                         @RequestParam String youtubeLink,
                                                         @RequestParam String title,
                                                         @RequestParam String footerColor,
                                                         @RequestParam(required = false) MultipartFile footerImage) {
        try {
            Footer updatedFooter = new Footer();
            updatedFooter.setSocialIcon(socialIcon);
            updatedFooter.setInstagramLink(instagramLink);
            updatedFooter.setFacebookLink(facebookLink);
            updatedFooter.setTwitterLink(twitterLink);
            updatedFooter.setYoutubeLink(youtubeLink);
            updatedFooter.setTitle(title);
            updatedFooter.setFooterColor(footerColor);

            Footer result = footerService.updateFooterByInstitutecode(institutecode, updatedFooter, footerImage);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload footer image: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

                  // Delete a Footer by institutecode
     @DeleteMapping("/deleteFooter")
    public ResponseEntity<String> deleteFooter(@RequestParam String institutecode) {
        footerService.deleteFooter(institutecode);
        return ResponseEntity.ok("Footer deleted successfully.");
    }

                // Get the Footer by institutecode
    @GetMapping("/getFooterByInstitutecode")
    public ResponseEntity<Footer> getFooterByInstitutecode(@RequestParam String institutecode) {
        return footerService.getFooterByInstitutecode(institutecode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

//    // Get all Footers by institutecode
//    @GetMapping("/getAllFooters")
//    public ResponseEntity<Optional<List<Footer>>> getAllFooters(@RequestParam String institutecode) {
//        Optional<List<Footer>> footers = footerService.getAllFooters(institutecode);
//        return footers.isPresent()
//                ? ResponseEntity.ok(footers)
//                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
//    }



    // AddInstagramLink
    @PostMapping("/addInstagramLink")
    public ResponseEntity<?> addInstagramLink(@RequestParam String institutecode, @RequestParam String instagramLink) {
        Footer footer = footerService.addOrUpdateSocialLink(institutecode, "instagramLink", instagramLink);
        return footer != null ? ResponseEntity.ok(footer) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }
                     //updateInstagramLink
    @PutMapping("/updateInstagramLink")
    public ResponseEntity<?> updateInstagramLink(@RequestParam String institutecode, @RequestParam String instagramLink) {
        Footer footer = footerService.addOrUpdateSocialLink(institutecode, "instagramLink", instagramLink);
        return footer != null ? ResponseEntity.ok(footer) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @DeleteMapping("/deleteInstagramLink")
    public ResponseEntity<String> deleteInstagramLink(@RequestParam String institutecode) {
        boolean isDeleted = footerService.deleteSocialLink(institutecode, "instagramLink");
        return isDeleted ? ResponseEntity.ok("Instagram link deleted successfully.") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @GetMapping("/getInstagramLink")
    public ResponseEntity<String> getInstagramLink(@RequestParam String institutecode) {
        String instagramLink = footerService.getSocialLink(institutecode, "instagramLink");
        return instagramLink != null ? ResponseEntity.ok(instagramLink) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instagram link not found.");
    }



                    // Add/Update Facebook link
    @PostMapping("/addFacebookLink")
    public ResponseEntity<?> addFacebookLink(@RequestParam String institutecode, @RequestParam String facebookLink) {
        Footer footer = footerService.addOrUpdateSocialLink(institutecode, "facebookLink", facebookLink);
        return footer != null ? ResponseEntity.ok(footer) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @PutMapping("/updateFacebookLink")
    public ResponseEntity<?> updateFacebookLink(@RequestParam String institutecode, @RequestParam String facebookLink) {
        Footer footer = footerService.addOrUpdateSocialLink(institutecode, "facebookLink", facebookLink);
        return footer != null ? ResponseEntity.ok(footer) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @DeleteMapping("/deleteFacebookLink")
    public ResponseEntity<String> deleteFacebookLink(@RequestParam String institutecode) {
        boolean isDeleted = footerService.deleteSocialLink(institutecode, "facebookLink");
        return isDeleted ? ResponseEntity.ok("Facebook link deleted successfully.") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @GetMapping("/getFacebookLink")
    public ResponseEntity<String> getFacebookLink(@RequestParam String institutecode) {
        String facebookLink = footerService.getSocialLink(institutecode, "facebookLink");
        return facebookLink != null ? ResponseEntity.ok(facebookLink) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Facebook link not found.");
    }

                              // Add/Update Twitter link
    @PostMapping("/addTwitterLink")
    public ResponseEntity<?> addTwitterLink(@RequestParam String institutecode, @RequestParam String twitterLink) {
        Footer footer = footerService.addOrUpdateSocialLink(institutecode, "twitterLink", twitterLink);
        return footer != null ? ResponseEntity.ok(footer) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @PutMapping("/updateTwitterLink")
    public ResponseEntity<?> updateTwitterLink(@RequestParam String institutecode, @RequestParam String twitterLink) {
        Footer footer = footerService.addOrUpdateSocialLink(institutecode, "twitterLink", twitterLink);
        return footer != null ? ResponseEntity.ok(footer) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @DeleteMapping("/deleteTwitterLink")
    public ResponseEntity<String> deleteTwitterLink(@RequestParam String institutecode) {
        boolean isDeleted = footerService.deleteSocialLink(institutecode, "twitterLink");
        return isDeleted ? ResponseEntity.ok("Twitter link deleted successfully.") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @GetMapping("/getTwitterLink")
    public ResponseEntity<String> getTwitterLink(@RequestParam String institutecode) {
        String twitterLink = footerService.getSocialLink(institutecode, "twitterLink");
        return twitterLink != null ? ResponseEntity.ok(twitterLink) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Twitter link not found.");
    }



                        // Add/Update YouTube link
    @PostMapping("/addYouTubeLink")
    public ResponseEntity<?> addYouTubeLink(@RequestParam String institutecode, @RequestParam String youtubeLink) {
        Footer footer = footerService.addOrUpdateSocialLink(institutecode, "youtubeLink", youtubeLink);
        return footer != null ? ResponseEntity.ok(footer) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @PutMapping("/updateYouTubeLink")
    public ResponseEntity<?> updateYouTubeLink(@RequestParam String institutecode, @RequestParam String youtubeLink) {
        Footer footer = footerService.addOrUpdateSocialLink(institutecode, "youtubeLink", youtubeLink);
        return footer != null ? ResponseEntity.ok(footer) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @DeleteMapping("/deleteYouTubeLink")
    public ResponseEntity<String> deleteYouTubeLink(@RequestParam String institutecode) {
        boolean isDeleted = footerService.deleteSocialLink(institutecode, "youtubeLink");
        return isDeleted ? ResponseEntity.ok("YouTube link deleted successfully.") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Footer not found.");
    }

    @GetMapping("/getYouTubeLink")
    public ResponseEntity<String> getYouTubeLink(@RequestParam String institutecode) {
        String youtubeLink = footerService.getSocialLink(institutecode, "youtubeLink");
        return youtubeLink != null ? ResponseEntity.ok(youtubeLink) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("YouTube link not found.");
    }




}


