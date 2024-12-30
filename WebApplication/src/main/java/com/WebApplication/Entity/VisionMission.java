package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class VisionMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vision", nullable = false, length = 500)
    private String vision;

    @Column(name = "mission", nullable = false, length = 500)
    private String mission;

    @Column(name = "institutecode", nullable = false, unique = true)
    private String institutecode;


}
