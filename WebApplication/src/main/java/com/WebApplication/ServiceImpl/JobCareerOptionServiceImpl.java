package com.WebApplication.ServiceImpl;



import com.WebApplication.Entity.JobCareerOption;
import com.WebApplication.Repository.JobCareerOptionRepository;
import com.WebApplication.Service.JobCareerOptionService;
import com.WebApplication.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JobCareerOptionServiceImpl implements JobCareerOptionService {

    @Autowired
    private JobCareerOptionRepository jobCareerOptionRepository;

    @Autowired
    private S3Service s3Service;

    @Override
    public JobCareerOption createJobCareerOption(JobCareerOption jobCareerOption, String institutecode, MultipartFile resume) {
        jobCareerOption.setInstitutecode(institutecode);
        jobCareerOption.setPostDate(LocalDate.now());

        if (resume != null && !resume.isEmpty()) {
            try {
                String resumeUrl = s3Service.uploadImage(resume);
                jobCareerOption.setResumeUrl(resumeUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload resume", e);
            }
        }

        return jobCareerOptionRepository.save(jobCareerOption);
    }



    @Override
    public JobCareerOption updateJobCareerOption(long id, JobCareerOption jobCareerOption, MultipartFile resume) {
        JobCareerOption existingJobCareerOption = jobCareerOptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobCareerOption not found with id: " + id));

        existingJobCareerOption.setTitle(jobCareerOption.getTitle());
        existingJobCareerOption.setDescription(jobCareerOption.getDescription());
        existingJobCareerOption.setLocation(jobCareerOption.getLocation());
        existingJobCareerOption.setSalaryRange(jobCareerOption.getSalaryRange());
        existingJobCareerOption.setResponsibilities(jobCareerOption.getResponsibilities());
        existingJobCareerOption.setJobCareerOptionColor(jobCareerOption.getJobCareerOptionColor());

        if (resume != null && !resume.isEmpty()) {
            try {
                String resumeUrl = s3Service.uploadImage(resume);
                existingJobCareerOption.setResumeUrl(resumeUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload resume", e);
            }
        }

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

        if (jobCareerOption.getTitle() != null) existingJobCareerOption.setTitle(jobCareerOption.getTitle());
        if (jobCareerOption.getDescription() != null) existingJobCareerOption.setDescription(jobCareerOption.getDescription());
        if (jobCareerOption.getLocation() != null) existingJobCareerOption.setLocation(jobCareerOption.getLocation());
        if (jobCareerOption.getSalaryRange() != null) existingJobCareerOption.setSalaryRange(jobCareerOption.getSalaryRange());
        if (jobCareerOption.getResponsibilities() != null) existingJobCareerOption.setResponsibilities(jobCareerOption.getResponsibilities());
        if (jobCareerOption.getJobCareerOptionColor() != null) existingJobCareerOption.setJobCareerOptionColor(jobCareerOption.getJobCareerOptionColor());
        if (jobCareerOption.getResumeUrl() != null) existingJobCareerOption.setResumeUrl(jobCareerOption.getResumeUrl()); // ✅ Update resumeUrl

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
