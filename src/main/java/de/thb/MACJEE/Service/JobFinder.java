package de.thb.MACJEE.Service;

import de.thb.MACJEE.Repository.*;
import de.thb.MACJEE.Entitys.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class JobFinder {

    private final CustomerRepository customerRepository;
    private final JobRepository jobRepository;

    public JobFinder(CustomerRepository customerRepository, JobRepository jobRepository) {
        this.customerRepository = customerRepository;
        this.jobRepository = jobRepository;
    }

    public List<Job> findPerfectJobs(Long customerId) {

        // 1. Lese den Customer aus der Datenbank
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        // 2. Lese alle Skills des Customers aus
        Set<CustomerSkill> customerSkills = customer.getSkills();

        // 3. Durchsuche die Datenbank nach passenden Jobs
        List<Job> matchingJobs = jobRepository.findBySkillsIn(customerSkills);

        return null;
    }
}

