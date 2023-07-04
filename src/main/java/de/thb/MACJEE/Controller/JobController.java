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
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

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
    public String applyToJob(@RequestParam("jobId") Long jobId, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
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

        //TODO: sending a notification or email to the company

        String referringURL = request.getHeader("referer");
        if (referringURL != null && referringURL.contains("/customer/job/perfect")) {
            return "redirect:/customer/job/perfect";
        } else {
            return "redirect:/job/all";
        }
    }
}
