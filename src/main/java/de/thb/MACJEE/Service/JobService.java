package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean addApplicant(Job job, Customer customer) {
        List<Customer> applicants = job.getApplicants();
        if (applicants.contains(customer)) {
            return false; // Customer is already an applicant
        }

        for (Customer applicant : applicants) {
            if (applicant.getId().equals(customer.getId())) {
                return false; // Customer is already an applicant
            }
        }

        applicants.add(customer);
        jobRepository.save(job);
        return true;
    }

    public boolean isCustomerApplicant(Long jobId, Customer customer) {
        List<Customer> applicants = jobRepository.findJobById(jobId).get().getApplicants();
        return applicants.contains(customer);
    }

    public Optional<Company> getCompanyOfJob(Long job) {
        return jobRepository.findCompanyByJobId(job);
    }

    //public List<Job> getJobsByCompany(Company company) {
    //    return jobRepository.findJobsByCompany(company);
    //}

    public List<Customer> getApplicantsOfJob(Long jobId) {
        return jobRepository.findApplicantsByJobId(jobId);
    }

    public Optional<Job> getJobByCompany(Company company) {
        return jobRepository.findJobByCompany(company);
    }

    public void setAvailabilityStateOfJob(boolean state, Job job) {
        job.setOpen(state);
        jobRepository.save(job);
    }

    public void denyApplicantOfJob(Job job, Customer applicant) {
        List<Customer> applicants = job.getApplicants();
        applicants.remove(applicant);
        jobRepository.save(job);
    }
}