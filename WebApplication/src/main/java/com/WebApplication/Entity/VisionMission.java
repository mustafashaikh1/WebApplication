package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "WebVisionMission")
public class VisionMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vision", nullable = false, length = 1500)
    private String vision;

    @Column(name = "mission", nullable = false, length = 1500)
    private String mission;

    private String visionmissionColor;

    @Column(name = "institutecode", nullable = false, unique = true)
    private String institutecode;

    @Column(name = "directorMessage", length = 2000)
    private String directorMessage;

    @Column(name = "directorName", length = 255)
    private String directorName;

    @Column(name = "directorImage") // S3 Image URL
    private String directorImage;

    @Column(name = "description", length = 3000)
    private String description;


}
