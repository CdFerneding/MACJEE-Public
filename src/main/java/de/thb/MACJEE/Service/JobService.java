package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Repository.JobRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    //public List<Job> getJobsByCompany(Company company) {
    //    return jobRepository.findJobsByCompany(company);
    //}
}