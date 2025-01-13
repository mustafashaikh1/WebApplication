package com.WebApplication.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Topper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topperId;

       private String name;
       private Double totalMarks;
       private  String post;
      @Column(name = "`rank`")
      private Integer rank;
       private Integer year;
      private String topperImage;

    private String topperColor;

    //
//    @Column(name="institute_code")
    private String institutecode;

}
