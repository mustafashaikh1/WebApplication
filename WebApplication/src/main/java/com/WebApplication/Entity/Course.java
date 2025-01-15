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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;


    private String courseTitle;
    @Column(length = 500) // Update this length based on your database column length
    private String link;

    private String courseImage;
    private String description;


    private String courseColor;
    private String institutecode;

}
