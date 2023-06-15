package de.thb.MACJEE.Service;

import de.thb.MACJEE.Repository.*;
import de.thb.MACJEE.Entitys.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Data
public class JobFinder {
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final JobRepository jobRepository;

    public List<Job> findPerfectJobs(Long customerId) throws Exception{

        // 1. Lese den Customer aus der Datenbank
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        // 2. Lese alle Skills des Customers aus
        Set<Skill> customerSkills = customer.getSkills();

        // 3. Durchsuche die Datenbank nach passenden Jobs
        List<Job> matchingJobs = jobRepository.findBySkillsIn(customerSkills);

        return matchingJobs;
    }
}

