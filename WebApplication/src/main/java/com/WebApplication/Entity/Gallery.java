package com.WebApplication.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name ="WebGallery")
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long galleryId;

    private String eventName;
    private Integer year;
    private String galleryImage;
    private String galleryColor;

    private String institutecode;



}
