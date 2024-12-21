package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ManuBar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manuBarId;

    private String manuBarColor;

    private  String institutecode;

//    @ManyToOne
//    @JoinColumn(name = "course_id")
//    private Course course;
}
