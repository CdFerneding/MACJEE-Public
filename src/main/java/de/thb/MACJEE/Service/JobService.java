package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Repository.CustomerRepository;
import de.thb.MACJEE.Repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Job> getAllJobs() {
        return (List<Job>) jobRepository.findAll();
    }

    // Transactional needed to load the required Skills if a job is loaded
    @Transactional
    public List<Job> getOpenJobs() {
        return jobRepository.findJobsByIsOpen(true);
    }

    public Optional<Job> getJobById(Long jobId) {
        return jobRepository.findJobByIdWithApplicants(jobId);
    }

    public boolean addApplicant(Job job, Customer customer) {
        List<Customer> applicants = job.getApplicants();

        //check if customer is already an applicant
        for (Customer applicant : applicants) {
            if (applicant.getId().equals(customer.getId())) {
                return false; // Customer is already an applicant
            }
        }
        // check if the customer already has a job
        if(customerRepository.customerHasCurrentJob(customer)) {
            return false;
        }
        // check if the job already has a currently working customer
        if (job.isOpen() == false) {
            return false;
        }
        applicants.add(customer);
        jobRepository.save(job);

        List<Job> applications = customer.getApplications();
        applications.add(job);
        customerRepository.save(customer);

        return true;
    }

    public boolean isCustomerApplicant(Long jobId, Customer customer) {
        List<Customer> applicants = jobRepository.findJobByIdWithApplicants(jobId).get().getApplicants();
        return applicants.contains(customer);
    }

    public Optional<Company> getCompanyOfJob(Long job) {
        return jobRepository.findCompanyByJobId(job);
    }

    //public List<Job> getJobsByCompany(Company company) {
    //    return jobRepository.findJobsByCompany(company);
    //}

    public List<Customer> getApplicantsOfJob(Long jobId) {
        return jobRepository.findApplicantsWithSkillsByJobId(jobId);
    }

    public Optional<Job> getJobByCompany(Company company) {
        return jobRepository.findJobByCompany(company);
    }

    public void setAvailabilityStateOfJob(boolean state, Job job) {
        job.setOpen(state);
        jobRepository.save(job);
    }

    public Optional<Job> getJobWithApplicants(Long jobId) {
        return jobRepository.findJobWithApplicants(jobId);
    }

    public Optional<Job> getJobWithRequiredSkills(Long jobId) { return jobRepository.findJobWithRequiredSkills(jobId); }

    public List<Job> getPerfectJob(Customer customer) {
        List<Job> openJobs = getOpenJobs();
        if(!openJobs.isEmpty()) {
            List<Job> perfectJobs = new ArrayList<>();
            perfectJobs.add(openJobs.get(0));
            perfectJobs.add(openJobs.get(1));
            return perfectJobs;
        }
        else return null;
    }
}