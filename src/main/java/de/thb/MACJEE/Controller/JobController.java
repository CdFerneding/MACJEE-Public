package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Exeption.JobNotAvailableExeption;
import de.thb.MACJEE.Service.CompanyService;
import de.thb.MACJEE.Service.CustomerService;
import de.thb.MACJEE.Service.JobService;
import de.thb.MACJEE.Service.UserEntityService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/job")
@Data
public class JobController {
    @Autowired
    private final JobService jobService;
    @Autowired
    private final CompanyService companyService;
    @Autowired
    private final UserEntityService userEntityService;
    @Autowired
    private final CustomerService customerService;

    @GetMapping("/all")
    public String showAllOpenJobs(Model model) {
        List<Job> jobs = jobService.getOpenJobs();
        model.addAttribute("jobs", jobs);
        return "user/openJobs";
    }

    @PostMapping("/apply")
    public void applyToJob(@RequestParam("jobId") Long jobId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Customer applicant = customerService.getCustomerByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found."));

        Job job = jobService.getJobById(jobId)
                .orElseThrow(() -> new JobNotAvailableExeption("Job not found or not available"));

        jobService.addApplicant(job, applicant);

        Company company = job.getCompany();

        model.addAttribute("success", "Erfolgreich als \"" + job.getTitle() + "\" bei der Firma: \"" + company + "\" beworben!");

        // send an email to the company.mail to link the application review site:
        /*String companyEmail = company.getMail();

        String subject = "New Job Applicant";
        String message = "A new applicant has applied for the job:\n" +
                "Job Title: " + job.getTitle() + "\n" +
                "Applicant Name: " + applicant.getFirstName() + " "+ applicant.getLastName() + "\n" +
                "Username on MACJEE: " + applicant.getUsername() + "\n" +
                "Applicant Email: " + applicant.getMail() + "\n";

        // emailservice does not exist yet
        emailService.sendEmail(companyEmail, subject, message);*/

        // send a macjee notification to the company to link the application review site:


    }
}
