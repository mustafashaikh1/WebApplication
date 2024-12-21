package com.WebApplication.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Footer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long footerId;

    private String socialIcon;
    private String link;
    private String title;
    private String footerColor;
    private String institutecode; // New field
}
