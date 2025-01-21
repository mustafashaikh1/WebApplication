    package com.WebApplication.Entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Table(name = "WebUrlMapping")
    public class UrlMapping {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String dynamicPart;

        @Column(nullable = false)
        private String institutecode;
    }
