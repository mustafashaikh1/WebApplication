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
    public ResponseEntity<?> createFooter(      @RequestParam String title,
                                                @RequestParam String footerColor,
                                                @RequestParam String institutecode
    )
//                                            @RequestParam String instagramIcon,
//                                          @RequestParam String facebookIcon,
//                                          @RequestParam String twitterIcon,
//                                          @RequestParam String youtubeIcon,
//
////
////                                          @RequestParam String instagramLink,
////                                          @RequestParam String facebookLink,
////                                          @RequestParam String twitterLink,
////                                          @RequestParam String youtubeLink,
                                         {
        try {
            if (footerService.existsByInstitutecode(institutecode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A Footer with the given institutecode already exists.");
            }

            Footer footer = new Footer();

            footer.setTitle(title);
            footer.setFooterColor(footerColor);
            footer.setInstitutecode(institutecode);
//            footer.setInstagramIcon(instagramIcon);
//            footer.setFacebookIcon(facebookIcon);
//            footer.setTwitterIcon(twitterIcon);
//            footer.setYoutubeIcon(youtubeIcon);

//            footer.setInstagramLink(instagramLink);
//            footer.setFacebookLink(facebookLink);
//            footer.setTwitterLink(twitterLink);
//            footer.setYoutubeLink(youtubeLink);


            Footer createdFooter = footerService.saveFooter(footer, institutecode);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFooter);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateFooter")
    public ResponseEntity<?> updateFooter(    @RequestParam String title,
                                              @RequestParam String footerColor,
                                              @RequestParam String institutecode) {
        try {
            Footer updatedFooter = new Footer();
//            updatedFooter.setInstagramIcon(instagramIcon);
//            updatedFooter.setFacebookIcon(facebookIcon);
//            updatedFooter.setTwitterIcon(twitterIcon);
//            updatedFooter.setYoutubeIcon(youtubeIcon);
            updatedFooter.setTitle(title);
            updatedFooter.setFooterColor(footerColor);
//            updatedFooter.setInstagramLink(instagramLink);
//            updatedFooter.setFacebookLink(facebookLink);
//            updatedFooter.setTwitterLink(twitterLink);
//            updatedFooter.setYoutubeLink(youtubeLink);

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


    // Instagram Operations
    @PostMapping("/footer/instagram")
    public ResponseEntity<?> postInstagram(@RequestParam String institutecode,
                                           @RequestParam String icon,
                                           @RequestParam String link) {
        try {
            Footer footer = footerService.postInstagram(institutecode, icon, link);
            return ResponseEntity.status(HttpStatus.CREATED).body(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/footer/instagram")
    public ResponseEntity<?> updateInstagram(@RequestParam String institutecode,
                                             @RequestParam String icon,
                                             @RequestParam String link) {
        try {
            Footer footer = footerService.updateInstagram(institutecode, icon, link);
            return ResponseEntity.ok(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/footer/instagram")
    public ResponseEntity<?> deleteInstagram(@RequestParam String institutecode) {
        try {
            footerService.deleteInstagram(institutecode);
            return ResponseEntity.ok("Instagram details deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Facebook Operations
    @PostMapping("/footer/facebook")
    public ResponseEntity<?> postFacebook(@RequestParam String institutecode,
                                          @RequestParam String icon,
                                          @RequestParam String link) {
        try {
            Footer footer = footerService.postFacebook(institutecode, icon, link);
            return ResponseEntity.status(HttpStatus.CREATED).body(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/footer/facebook")
    public ResponseEntity<?> updateFacebook(@RequestParam String institutecode,
                                            @RequestParam String icon,
                                            @RequestParam String link) {
        try {
            Footer footer = footerService.updateFacebook(institutecode, icon, link);
            return ResponseEntity.ok(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/footer/facebook")
    public ResponseEntity<?> deleteFacebook(@RequestParam String institutecode) {
        try {
            footerService.deleteFacebook(institutecode);
            return ResponseEntity.ok("Facebook details deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Twitter Operations
    @PostMapping("/footer/twitter")
    public ResponseEntity<?> postTwitter(@RequestParam String institutecode,
                                         @RequestParam String icon,
                                         @RequestParam String link) {
        try {
            Footer footer = footerService.postTwitter(institutecode, icon, link);
            return ResponseEntity.status(HttpStatus.CREATED).body(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/footer/twitter")
    public ResponseEntity<?> updateTwitter(@RequestParam String institutecode,
                                           @RequestParam String icon,
                                           @RequestParam String link) {
        try {
            Footer footer = footerService.updateTwitter(institutecode, icon, link);
            return ResponseEntity.ok(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/footer/twitter")
    public ResponseEntity<?> deleteTwitter(@RequestParam String institutecode) {
        try {
            footerService.deleteTwitter(institutecode);
            return ResponseEntity.ok("Twitter details deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // YouTube Operations
    @PostMapping("/footer/youtube")
    public ResponseEntity<?> postYouTube(@RequestParam String institutecode,
                                         @RequestParam String icon,
                                         @RequestParam String link) {
        try {
            Footer footer = footerService.postYouTube(institutecode, icon, link);
            return ResponseEntity.status(HttpStatus.CREATED).body(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/footer/youtube")
    public ResponseEntity<?> updateYouTube(@RequestParam String institutecode,
                                           @RequestParam String icon,
                                           @RequestParam String link) {
        try {
            Footer footer = footerService.updateYouTube(institutecode, icon, link);
            return ResponseEntity.ok(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/footer/youtube")
    public ResponseEntity<?> deleteYouTube(@RequestParam String institutecode) {
        try {
            footerService.deleteYouTube(institutecode);
            return ResponseEntity.ok("YouTube details deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
