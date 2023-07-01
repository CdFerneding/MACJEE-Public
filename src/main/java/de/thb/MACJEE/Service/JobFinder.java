package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Enumerations.Characteristics;
import de.thb.MACJEE.Repository.*;
import de.thb.MACJEE.Entitys.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
public class JobFinder {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobService jobService;

    public List<Job> findPerfectJobs(Long customerId) throws Exception {

        // 1. Lese den Customer aus der Datenbank
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        // 2. Lese alle Skills des Customers aus
        List<Skill> customerSkills = customer.getSkills();

        // 3. Durchsuche die Datenbank nach passenden Jobs
        List<Job> matchingJobs = jobRepository.findBySkillsIn(customerSkills);

        return matchingJobs;
    }

    public List<Job> getPerfectJobs(Customer customer) {
        List<Job> openJobs = jobService.getOpenJobs();
        Map<Job, Integer> jobDiffMap = new HashMap<>();

        if (!openJobs.isEmpty()) {
            List<Skill> skills = customer.getSkills();

            for (Job job : openJobs) {
                List<Skill> requiredSkills = job.getRequiredSkills();

                // calculate difference between skill lists
                Integer difference = calculateDifference(skills, requiredSkills);
                if (difference == null) {
                    // if there is a problem with calculating the difference the job is marked as under-qualified
                    jobDiffMap.put(job, Characteristics.getNumberOfCharacteristics()*15);
                }
                jobDiffMap.put(job, difference);
            }
        } else return null;

        // sort the map entries based on the absolute difference to 0 ...


        // add the first 5 jobs to
        List<Job> matchingJobs = new ArrayList<>();
        int count = 0;
        if(jobDiffMap.size() < 5) {
            count = jobDiffMap.size();
        }

        for (Map.Entry<Job, Integer> entry : jobDiffMap.entrySet()) {
            if (count >= 5) {
                break;  // Exit the loop once 5 jobs have been added
            }

            Job job = entry.getKey();
            matchingJobs.add(job);

            count++;
        }

        return matchingJobs;
    }


    /**
     * @param skills
     * @param requiredSkills
     * @return integer value that indicates:
     * below zero: over-qualified
     * above zero: under-qualified
     * the closer the value is to zero the more the customer matches the job
     */
    private Integer calculateDifference(List<Skill> skills, List<Skill> requiredSkills) {
        if (requiredSkills == null || skills == null) {
            return null;
        }
        /**
         * when no skills are required all skills of customer are essentially over-qualification
         * note that if a job has no required skills the customer will be greatly overqualified!!
         * (meaning are high value below zero is returned)
         */
        if (skills.size() != 0) {
            if (requiredSkills.size() == 0) {
                int sum = 0;
                for (Skill skill : skills) {
                    sum += skill.getLevel();
                }
                return -1 * sum;
            }
        }
        if (skills.size() == 0) {
            return null;
        }
        // either the job or the customer skills are not fully initialized
        int numberOfSkills = Characteristics.getNumberOfCharacteristics();
        if(skills.size() != numberOfSkills || requiredSkills.size() != numberOfSkills) {
            return null;
        }
        int[] skillsDiff = new int[skills.size()];
        // calculate the difference between individual skills
        for (int i = 0; i < skills.size(); i++) {
            int differenceOfSkill = (int) (requiredSkills.get(i).getLevel() - skills.get(i).getLevel());
            skillsDiff[i] = differenceOfSkill;
        }
        // add the individual differences to get the overall difference
        int jobDiff = 0;
        for (int i = 0; i < skills.size(); i++) {
            /* no differentiation between positive and negative difference!
             * Because if you are over-qualified for one skill the skillDiff will be below zero.
             * It will cancel out the skills that are above zero (the skills where the customer is under-qualified).
             */
            jobDiff += skillsDiff[i];
        }
        return jobDiff;
    }
}

