package com.WebApplication.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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


    @Size(max = 500) // Limit the string size to 500 characters
    @Column(length = 500)
    private String maps; // New field for map-related data

    private String institutecode;


}
