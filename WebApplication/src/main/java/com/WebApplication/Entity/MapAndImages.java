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
@Table(name = "WebMapAndImages")
public class MapAndImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contactImage;

    @Size(max = 500)
    @Column(length = 500)
    private String maps;

    private String institutecode;


}
