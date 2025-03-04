package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "WebGallery")
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long galleryId;

    private String eventName;
    private Integer year;
    private String galleryColor;
    private String institutecode;

    // List of gallery image URLs
    @ElementCollection
    @CollectionTable(name = "gallery_images", joinColumns = @JoinColumn(name = "gallery_id"))
    @Column(name = "image_url")
    private List<String> galleryImages = new ArrayList<>();

    // List of image URL IDs stored as integers
    @ElementCollection
    @CollectionTable(name = "gallery_image_url_ids", joinColumns = @JoinColumn(name = "gallery_id"))
    @Column(name = "image_url_id")
    private List<Integer> imageUrlIds = new ArrayList<>();
}
