package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "WebFacilitytitle")
public class FacilityTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityTitleId;

    private String facilityTitle;
}
