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
public class SlideBar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slideBarColor;

    private String institutecode;


    @ElementCollection
    @CollectionTable(name = "slide_images", joinColumns = @JoinColumn(name = "slide_id"))
    @Column(name = "image_url")
    private List<String> slideImages;

}
