package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ManuBar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manuBarId;

    private String manuBarColor;

    private String institutecode;

    private String menubarImage;

    @ElementCollection
    @CollectionTable(name = "menu_items", joinColumns = @JoinColumn(name = "manuBarId"))
    @Column(name = "menu_item")
    private List<String> menuItems = new ArrayList<>();
}