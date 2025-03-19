package com.WebApplication.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @OneToOne
    @JoinColumn(name = "job_career_option_id", referencedColumnName = "id")
    @JsonManagedReference
    private JobCareerOption jobCareerOption;

    // Getters and Setters
    public JobCareerOption getJobCareerOption() {
        return jobCareerOption;
    }

    public void setJobCareerOption(JobCareerOption jobCareerOption) {
        this.jobCareerOption = jobCareerOption;
    }
}
