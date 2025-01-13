package com.WebApplication.ServiceImpl;



import com.WebApplication.Entity.JobCareerOption;
import com.WebApplication.Repository.JobCareerOptionRepository;
import com.WebApplication.Service.JobCareerOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JobCareerOptionServiceImpl implements JobCareerOptionService {

    @Autowired
    private JobCareerOptionRepository jobCareerOptionRepository;

    @Override
    public JobCareerOption createJobCareerOption(JobCareerOption jobCareerOption, String institutecode) {
        jobCareerOption.setInstitutecode(institutecode);
        jobCareerOption.setPostDate(LocalDate.now());
        return jobCareerOptionRepository.save(jobCareerOption);
    }





    @Override
    public JobCareerOption updateJobCareerOption(long id, JobCareerOption jobCareerOption) {
        JobCareerOption existingJobCareerOption = jobCareerOptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobCareerOption not found with id: " + id));
        existingJobCareerOption.setTitle(jobCareerOption.getTitle());
        existingJobCareerOption.setDescription(jobCareerOption.getDescription());
        existingJobCareerOption.setLocation(jobCareerOption.getLocation());
        existingJobCareerOption.setSalaryRange(jobCareerOption.getSalaryRange());
        existingJobCareerOption.setResponsibilities(jobCareerOption.getResponsibilities());
        existingJobCareerOption.setJobCareerOptionColor(jobCareerOption.getJobCareerOptionColor());
        return jobCareerOptionRepository.save(existingJobCareerOption);
    }

    @Override
    public JobCareerOption updateJobCareerOptionByInstitutecode(String institutecode, JobCareerOption jobCareerOption) {
        List<JobCareerOption> options = jobCareerOptionRepository.findByInstitutecode(institutecode);

        if (options.isEmpty()) {
            throw new RuntimeException("No JobCareerOption found for institutecode: " + institutecode);
        }

        // Assume you want to update all matching job career options for the given institutecode
        JobCareerOption existingJobCareerOption = options.get(0); // or iterate over the list if needed

        existingJobCareerOption.setTitle(jobCareerOption.getTitle());
        existingJobCareerOption.setDescription(jobCareerOption.getDescription());
        existingJobCareerOption.setLocation(jobCareerOption.getLocation());
        existingJobCareerOption.setSalaryRange(jobCareerOption.getSalaryRange());
        existingJobCareerOption.setResponsibilities(jobCareerOption.getResponsibilities());
        existingJobCareerOption.setJobCareerOptionColor(jobCareerOption.getJobCareerOptionColor());

        // Save and return the updated entity
        return jobCareerOptionRepository.save(existingJobCareerOption);
    }


    @Override
    public void deleteJobCareerOption(long id) {
        jobCareerOptionRepository.deleteById(id);
    }
    @Override
    public Optional<List<JobCareerOption>> getJobCareerOptionsByInstitutecode(String institutecode) {
        return Optional.of(jobCareerOptionRepository.findByInstitutecode(institutecode));
    }

    @Override
    public List<JobCareerOption> getAllJobCareerOptions() {
        return jobCareerOptionRepository.findAll();
    }
}
