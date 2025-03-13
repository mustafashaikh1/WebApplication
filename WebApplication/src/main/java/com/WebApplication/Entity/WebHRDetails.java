package com.WebApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "WebHRDetails")
public class WebHRDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hrName;
    private String email;
    private String contact;



    @ManyToOne // Make sure it's a valid relationship
    @JoinColumn(name = "job_career_option_id", nullable = false) // This must match DB column name
    private JobCareerOption jobCareerOption;

    // Getters and Setters
    public JobCareerOption getJobCareerOption() {
        return jobCareerOption;
    }

    public void setJobCareerOption(JobCareerOption jobCareerOption) {
        this.jobCareerOption = jobCareerOption;
    }



}
