package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "counter")
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "counter_name_1", nullable = false)
    private String counterName1;

    @Column(name = "count_value_1", nullable = false)
    private int countValue1;

    @Column(name = "counter_color_1")
    private String counterColor1;

    @Column(name = "counter_name_2", nullable = false)
    private String counterName2;

    @Column(name = "count_value_2", nullable = false)
    private int countValue2;

    @Column(name = "counter_color_2")
    private String counterColor2;

    @Column(name = "counter_name_3", nullable = false)
    private String counterName3;

    @Column(name = "count_value_3", nullable = false)
    private int countValue3;

    @Column(name = "counter_color_3")
    private String counterColor3;

    @Column(name = "institutecode", nullable = false)
    private String institutecode;
}
