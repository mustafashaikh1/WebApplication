package com.WebApplication.Service;

import com.WebApplication.Entity.JobCareerOption;
import com.WebApplication.Entity.MapAndImages;

import java.util.List;
import java.util.Optional;

public interface JobCareerOptionService {

    JobCareerOption createJobCareerOption(JobCareerOption jobCareerOption, String institutecode);

    JobCareerOption updateJobCareerOption(long id, JobCareerOption jobCareerOption);

    JobCareerOption updateJobCareerOptionByInstitutecode(String institutecode, JobCareerOption jobCareerOption);

    void deleteJobCareerOption(long id);

    Optional<List<JobCareerOption>> getJobCareerOptionsByInstitutecode(String institutecode);

    List<JobCareerOption> getAllJobCareerOptions();


}
