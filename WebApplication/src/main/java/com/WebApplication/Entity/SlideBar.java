package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "WebSlideBar")
public class SlideBar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slideBarColor;

    private String institutecode;

    // List of image URLs associated with the slide
    @ElementCollection
    @CollectionTable(name = "slide_images", joinColumns = @JoinColumn(name = "slide_id"))
    @Column(name = "image_url")
    private List<String> slideImages;

    // List of image URL IDs stored as integers
    @ElementCollection
    @CollectionTable(name = "image_url_ids", joinColumns = @JoinColumn(name = "slide_id"))
    @Column(name = "image_url_id")
    private List<Integer> imageUrlIds; // Now an Integer list

}
