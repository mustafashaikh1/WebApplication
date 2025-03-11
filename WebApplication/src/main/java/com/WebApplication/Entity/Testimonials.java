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
@Table(name = "WebTestimonials")
public class Testimonials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ensure ID generation is correct
    private Long id;

    private String testimonialColor;
    private String institutecode;

    // List of testimonial image URLs
    @ElementCollection
    @CollectionTable(name = "testimonial_images", joinColumns = @JoinColumn(name = "testimonial_id"))
    @Column(name = "image_url")
    private List<String> testimonialImages;

    // List of testimonial image IDs
    @ElementCollection
    @CollectionTable(name = "testimonial_image_ids", joinColumns = @JoinColumn(name = "testimonial_id"))
    @Column(name = "image_url_id")
    private List<Integer> imageUrlIds;

    // Testimonial details
    private String testimonialName;
    private String exam;
    private String post;
    private String description;
}
