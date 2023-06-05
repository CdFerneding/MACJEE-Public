package de.thb.MACJEE.Service;

import de.thb.MACJEE.Repository.*;
import de.thb.MACJEE.Entitys.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Data
public class JobFinder {

    private final CustomerRepository customerRepository;
    private final JobRepository jobRepository;

    public List<Job> findPerfectJobs(Long customerId) throws Exception{

        // 1. Lese den Customer aus der Datenbank
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        // 2. Lese alle Skills des Customers aus
        Set<CustomerSkill> customerSkills = customer.getSkills();

        // 3. Durchsuche die Datenbank nach passenden Jobs
        List<Job> matchingJobs = jobRepository.findBySkillsIn(customerSkills);

        return matchingJobs;
    }
}

