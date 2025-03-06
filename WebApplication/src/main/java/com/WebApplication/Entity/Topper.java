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
@Table(name = "WebTopper")
public class Topper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topperId;

    private String name;
    private Double totalMarks;
    private String post;

    @Column(name = "`rank`")
    private Integer rank;

    private Integer year;
    private String topperImage;
    private String topperColor;
    private String institutecode;

    // List of topper image URLs
    @ElementCollection
    @CollectionTable(name = "topper_images", joinColumns = @JoinColumn(name = "topper_id"))
    @Column(name = "image_url")
    private List<String> topperImages;

    // List of topper image IDs
    @ElementCollection
    @CollectionTable(name = "topper_image_ids", joinColumns = @JoinColumn(name = "topper_id"))
    @Column(name = "image_url_id")
    private List<Integer> imageUrlIds;
}
