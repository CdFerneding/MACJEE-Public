package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Exeption.JobHasNoCompanyException;
import de.thb.MACJEE.Exeption.JobNotFoundException;
import de.thb.MACJEE.Service.CompanyService;
import de.thb.MACJEE.Service.CustomerService;
import de.thb.MACJEE.Service.JobService;
import de.thb.MACJEE.Service.UserEntityService;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.hibernate.Hibernate;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/job")
@Data
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserEntityService userEntityService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public String showAllOpenJobs(Model model) {
        List<Job> jobs = jobService.getOpenJobs();
        // Initialize the requiredSkills collection for each job
        /*for (Job job : jobs) {
            Hibernate.initialize(job.getRequiredSkills());
        }*/
        model.addAttribute("jobs", jobs);
        return "job/openJobs";
    }

    @GetMapping("/viewJob")
    public String showJobs(Long id, Model model) {
        Job job = jobService.getJobByIdWithSkills(id).get();

        model.addAttribute("job", job);
        return "job/viewJob";
    }

    @PostMapping("/apply")
    public String applyToJob(@RequestParam("jobId") Long jobId, Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Customer applicant = customerService.getCustomerByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));

            Job job = jobService.getJobById(jobId)
                    .orElseThrow(() -> new JobNotFoundException("Job not found or job is not available."));

            Company company = jobService.getCompanyOfJob(job.getId())
                    .orElseThrow(() -> new JobHasNoCompanyException("This Job does not have a referenced Company."));

            if (jobService.addApplicant(job, applicant)) {
                String successMessage = "Erfolgreich als: \"" + job.getTitle() + "\" bei der Firma: \"" + company.getCompanyName() + "\" beworben!";
                redirectAttributes.addFlashAttribute("success", successMessage);
            } else {
                redirectAttributes.addFlashAttribute("error", "Du bist bereits ein Bewerber für diesen Job oder du hast bereits einen Job.");
            }
        } catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Benutzername nicht gefunden");
        } catch (JobNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Job nicht verfügbar");
        } catch (JobHasNoCompanyException e) {
            redirectAttributes.addFlashAttribute("error", "Der Job hat keine verlinkte Firma");
        }

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
        return "redirect:/job/all";
    }

    /*@GetMapping("/applicants")
    public String showCompanyApplicants(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));

            List<Job> companyJobs = companyService.getJobsByCompany(company);

        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Benutzername nicht gefunden");
        }
        return "job/applicants";
    }*/
}
