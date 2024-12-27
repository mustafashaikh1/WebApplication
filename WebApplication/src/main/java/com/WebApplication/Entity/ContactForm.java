package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ContactForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    private String name;
    private String mobileNo;
    private String course;
    private String description;
    private String email; // New field for email

    @Lob
    private String contactImage; // New field for contact image URL

    private String maps; // New field for map-related data

    private String institutecode;


}
