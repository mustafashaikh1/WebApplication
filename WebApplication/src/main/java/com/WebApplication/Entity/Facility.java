package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "WebFacility")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    private String facilityName;

    private Byte experienceInYear;

    private String subject;

    private String institutecode;

    private String facilityEducation;

    private String facilityImage;

    @Lob
    @Column(length = 5000)
    private String description;

    private String facilityColor;
}
