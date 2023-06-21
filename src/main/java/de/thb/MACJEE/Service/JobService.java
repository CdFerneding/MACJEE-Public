package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Repository.JobRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return (List<Job>) jobRepository.findAll();
    }
    public List<Job> getOpenJobs() {
        return jobRepository.findJobsByIsOpen(true);
    }

    public Optional<Job> getJobById(Long jobId) {
        return jobRepository.findJobById(jobId);
    }

    public void addApplicant(Job job, Customer applicant) {
        job.getApplicants().add(applicant);
        // searches through the jobs based on the id, if the job exists already it updates the row with the new data
        jobRepository.save(job);
    }


    //public List<Job> getJobsByCompany(Company company) {
    //    return jobRepository.findJobsByCompany(company);
    //}
}