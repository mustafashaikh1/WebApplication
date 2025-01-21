package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "WebFooter")
public class Footer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long footerId;

    private String title;
    private String footerColor;

    private String instagramLink;
    private String facebookLink;
    private String twitterLink;
    private String youtubeLink;

    private String whatsappLink;  
    private String institutecode;

    private String email;
    private String mobileNumber;
    private String address;
}
