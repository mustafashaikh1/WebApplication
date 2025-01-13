package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Awards")
public class AwardsAndAccolades{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String awardName;
    private String description;
    private String awardedBy;
    private int year;
    private String  awardImage;

}
