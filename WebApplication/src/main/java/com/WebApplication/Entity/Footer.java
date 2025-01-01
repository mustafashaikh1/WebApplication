package com.WebApplication.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Footer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long footerId;

    private String title;
    private String footerColor;

    private String instagramIcon;
    private String instagramLink;

    private String facebookIcon;
    private String facebookLink;

    private String twitterIcon;
    private String twitterLink;

    private String youtubeIcon;
    private String youtubeLink;

    private String institutecode;







}
