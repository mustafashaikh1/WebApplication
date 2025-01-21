package com.WebApplication.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "WebAboutUs")
public class AboutUs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AboutUsId;

    private  String AboutUsTitle;
    @Lob
    @Column(length = 5000)
    private String description;
    private String AboutUsImage;


    private String institutecode;

}
