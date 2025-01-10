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
    public ResponseEntity<?> createFooter(@RequestParam String title,
                                          @RequestParam String footerColor,
                                          @RequestParam String institutecode,
                                          @RequestParam String email,
                                          @RequestParam String mobileNumber,
                                          @RequestParam String address)
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
            footer.setEmail(email); // Set email
            footer.setMobileNumber(mobileNumber);
            footer.setAddress(address);// Set mobile number

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
                                          @RequestParam String address)

    {
        try {
            Footer updatedFooter = new Footer();
            updatedFooter.setTitle(title);
            updatedFooter.setFooterColor(footerColor);
            updatedFooter.setEmail(email); // Set email
            updatedFooter.setMobileNumber(mobileNumber); // Set mobile number
            updatedFooter.setAddress(address); // Set address

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

                                           @RequestParam String link) {
        try {
            Footer footer = footerService.postInstagram(institutecode,  link);
            return ResponseEntity.status(HttpStatus.CREATED).body(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/footer/instagram")
    public ResponseEntity<?> updateInstagram(@RequestParam String institutecode,

                                             @RequestParam String link) {
        try {
            Footer footer = footerService.updateInstagram(institutecode,  link);
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

                                          @RequestParam String link) {
        try {
            Footer footer = footerService.postFacebook(institutecode, link);
            return ResponseEntity.status(HttpStatus.CREATED).body(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/footer/facebook")
    public ResponseEntity<?> updateFacebook(@RequestParam String institutecode,

                                            @RequestParam String link) {
        try {
            Footer footer = footerService.updateFacebook(institutecode,  link);
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

                                         @RequestParam String link) {
        try {
            Footer footer = footerService.postTwitter(institutecode,  link);
            return ResponseEntity.status(HttpStatus.CREATED).body(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/footer/twitter")
    public ResponseEntity<?> updateTwitter(@RequestParam String institutecode,

                                           @RequestParam String link) {
        try {
            Footer footer = footerService.updateTwitter(institutecode,  link);
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

                                         @RequestParam String link) {
        try {
            Footer footer = footerService.postYouTube(institutecode, link);
            return ResponseEntity.status(HttpStatus.CREATED).body(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/footer/youtube")
    public ResponseEntity<?> updateYouTube(@RequestParam String institutecode,

                                           @RequestParam String link) {
        try {
            Footer footer = footerService.updateYouTube(institutecode,  link);
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

    // WhatsApp Operations
    @PostMapping("/footer/whatsapp")
    public ResponseEntity<?> postWhatsApp(@RequestParam String institutecode,
                                          @RequestParam String link) {
        try {
            Footer footer = footerService.postWhatsApp(institutecode, link);
            return ResponseEntity.status(HttpStatus.CREATED).body(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/footer/whatsapp")
    public ResponseEntity<?> updateWhatsApp(@RequestParam String institutecode,
                                            @RequestParam String link) {
        try {
            Footer footer = footerService.updateWhatsApp(institutecode, link);
            return ResponseEntity.ok(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/footer/whatsapp")
    public ResponseEntity<?> deleteWhatsApp(@RequestParam String institutecode) {
        try {
            footerService.deleteWhatsApp(institutecode);
            return ResponseEntity.ok("WhatsApp details deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
