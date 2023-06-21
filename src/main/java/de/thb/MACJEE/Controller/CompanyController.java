package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Exeption.JobNotFoundExeption;
import de.thb.MACJEE.Service.CompanyService;
import de.thb.MACJEE.Service.CustomerService;
import de.thb.MACJEE.Service.JobService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/company")
@Data
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobService jobService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public String showCompanyProfile(@PathVariable("id") Long id, Model model) {
        Company company = companyService.getCompanyById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("company", company);
        return "user/companyProfile";
    }

    @GetMapping("/jobs")
    public String showCompanyJobs(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));

            List<Job> companyJobs = companyService.getJobsByCompany(company);

            model.addAttribute("company", company);
            model.addAttribute("jobs", companyJobs);
        } catch(UsernameNotFoundException e) {
            model.addAttribute("error", "nutzer nicht gefunden.");
        }
        return "job/companyJobs";
    }

    @GetMapping("/jobs/{id}")
    public String showApplicantsOfCompanyJob(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));
            Job job = jobService.getJobById(id)
                    .orElseThrow(() -> new JobNotFoundExeption("job not found."));
            List<Customer> applicants = jobService.getApplicantsOfJob(job.getId());

            model.addAttribute("applicants", applicants);
            model.addAttribute("job", job);
            model.addAttribute("company", company);
        } catch(UsernameNotFoundException e) {
            model.addAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundExeption e) {
            model.addAttribute("error", "Job wurde nicht gefunden.");
        }
        return "job/applicants";
    }

    @PostMapping("/jobs/{id}/accept")
    public String acceptApplicant(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));
            Job job = jobService.getJobById(id)
                    .orElseThrow(() -> new JobNotFoundExeption("job not found."));
            jobService.setAvailabilityStateOfJob(false, job);
        } catch(UsernameNotFoundException e) {
            model.addAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundExeption e) {
            model.addAttribute("error", "Job wurde nicht gefunden.");
        }
        return "redirect:company/jobs";
    }

    @PostMapping("/jobs/{id}/deny")
    public String denyApplicant(@PathVariable("id") Long id, @RequestParam("username") String applicantUsername, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));
            Job job = jobService.getJobById(id)
                    .orElseThrow(() -> new JobNotFoundExeption("job not found."));
            Customer applicant = customerService.getCustomerByUserName(applicantUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("customer not found"));
            jobService.denyApplicantOfJob(job, applicant);
        } catch(UsernameNotFoundException e) {
            model.addAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundExeption e) {
            model.addAttribute("error", "Job wurde nicht gefunden.");
        }
        return "redirect:company/jobs";
    }


}
