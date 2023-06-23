package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Exeption.JobNotFoundException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showCompanyJobs(Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));

            List<Job> companyJobs = companyService.getJobsByCompany(company);

            model.addAttribute("company", company);
            model.addAttribute("jobs", companyJobs);
        } catch(UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "nutzer nicht gefunden.");
        }
        return "job/companyJobs";
    }

    @GetMapping("/jobs/{id}")
    public String showApplicantsOfCompanyJob(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));
            Job job = jobService.getJobById(id)
                    .orElseThrow(() -> new JobNotFoundException("job not found."));
            List<Job> companyJobs = companyService.getJobsByCompany(company);

            boolean isJobPartOfCompanyJobs = false;

            for (Job companyJob : companyJobs) {
                if (companyJob.getId().equals(job.getId())) {
                    isJobPartOfCompanyJobs = true;
                    break;
                }
            }

            if (isJobPartOfCompanyJobs) {
                List<Customer> applicants = jobService.getApplicantsOfJob(job.getId());
                model.addAttribute("applicants", applicants);
                model.addAttribute("job", job);
                model.addAttribute("company", company);
            } else {
                // Currently displayed Job does not belong to the company currently logged in
                // Display error page because this state is only reachable through manual inputted URL
                return "error";
            }
        } catch(UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Job wurde nicht gefunden.");
        }
        return "job/applicants";
    }

    @PostMapping("/jobs/{id}/accept")
    public String acceptApplicant(@PathVariable("id") Long id, @RequestParam("username") String applicantUsername,
                                  Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            /*Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));*/
            Job job = jobService.getJobWithApplicants(id)
                    .orElseThrow(() -> new JobNotFoundException("job not found."));
            Customer applicant = customerService.getCustomerWithApplications(applicantUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("customer not found."));
            // if the customer does already have a job (currentJob) he will get accepted as the worker of the job
            if(customerService.CustomerHasJob(applicant)) {
                redirectAttributes.addFlashAttribute("error", "Bewerber hat bereits einen Job.");
                return "redirect:/company/jobs";
            }
            customerService.setCustomerWorkingAt(applicant, job);
            // send the applicant a message...
            jobService.setAvailabilityStateOfJob(false, job);
            customerService.removeApplication(job, applicant);
            redirectAttributes.addFlashAttribute("success", "Bewerber wurde angenommen.");
        } catch(UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Job wurde nicht gefunden.");
        }
        return "redirect:/company/jobs";
    }

    @PostMapping("/jobs/{id}/deny")
    public String denyApplicant(@PathVariable("id") Long id, @RequestParam("username") String applicantUsername,
                                Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));
            Job job = jobService.getJobById(id)
                    .orElseThrow(() -> new JobNotFoundException("job not found."));
            Customer applicant = customerService.getCustomerByUserName(applicantUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("customer not found"));
            jobService.denyApplicantOfJob(job, applicant);
            redirectAttributes.addFlashAttribute("success", "Bewerber wurde abgelehnt");
        } catch(UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Job wurde nicht gefunden.");
        }
        return "redirect:/company/jobs";
    }
}
