package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Controller.form.CompanySettingsForm;
import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Enumerations.Characteristics;
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

    @GetMapping("/profile")
    public String showCompanyProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Company company = companyService.getCompanyByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("company", company);
        return "user/companyProfile";
    }

    @GetMapping("/companySettings")
    public String showCompanySettings(@RequestParam("changes") String changes, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Company company = companyService.getCompanyByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("changes", changes);
        model.addAttribute("company", company);
        model.addAttribute("characteristics", Characteristics.values());
        return "user/companySettings";
    }

    @PostMapping("/companySettings")
    public String postCompanySettings(@RequestParam("changes") String changes, CompanySettingsForm companySettingsForm, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Company company = companyService.getCompanyByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        companyService.setAttributes(company, changes, companySettingsForm);

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
    public String showApplicantsOfCompanyJob(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));
            Job job = jobService.getJobWithRequiredSkills(id)
                    .orElseThrow(() -> new JobNotFoundException("job not found."));
            List<Job> companyJobs = companyService.getJobsByCompany(company);

            // Collection.contains is not usable because Objects can not be properly compared
            // since not all attributes are being loaded from the database
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
            model.addAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            model.addAttribute("error", "Job wurde nicht gefunden.");
        }
        return "job/applicants";
    }

    @GetMapping("/jobs/{id}/fire")
    public String fireCurrentWorker(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Company company = companyService.getCompanyByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));
            Job job = jobService.getJobById(id)
                    .orElseThrow(() -> new JobNotFoundException("job not found."));

            if(job.getWorking() != null) {
                jobService.fireCurrentWorker(job);
                jobService.setAvailabilityStateOfJob(true, job);
                redirectAttributes.addFlashAttribute("success", "Arbeiter wurde erfolgreich gekündigt.");
            } else {
                redirectAttributes.addFlashAttribute("error", "dieser Job hat keinen Arbeiter");
            }
        } catch(UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Job wurde nicht gefunden.");
        }
        return "redirect:/company/jobs";
    }

    @GetMapping("/jobs/{id}/delete")
    public String deleteJob(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // no test if job belongs to company because only the company can see it's own jobs in the first place
            Job job = jobService.getJobById(id)
                    .orElseThrow(() -> new JobNotFoundException("job not found."));

            jobService.deleteJob(job);
            String m = "Job '" + job.getTitle() + "' wurde gelöscht";
            redirectAttributes.addFlashAttribute("success", m);
        } catch(UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Job wurde nicht gefunden.");
        }
        return "redirect:/company/jobs";
    }

    @GetMapping("/jobs/{id}/close")
    public String closeJob(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // no test if job belongs to company because only the company can see it's own jobs in the first place
            Job job = jobService.getJobById(id)
                    .orElseThrow(() -> new JobNotFoundException("job not found."));

            jobService.setAvailabilityStateOfJob(false, job);
            String m = "Job '" + job.getTitle() + "' wurde geschlossen. Kunden können sich nun nicht mehr bewerben.";
            redirectAttributes.addFlashAttribute("success", m);
        } catch(UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Job wurde nicht gefunden.");
        }
        return "redirect:/company/jobs";
    }

    @GetMapping("/jobs/{id}/open")
    public String openJob(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // no test if job belongs to company because only the company can see it's own jobs in the first place
            Job job = jobService.getJobById(id)
                    .orElseThrow(() -> new JobNotFoundException("job not found."));

            jobService.setAvailabilityStateOfJob(true, job);
            String m = "Job '" + job.getTitle() + "' wurde geöffnet und Kunden können sich bewerben.";
            redirectAttributes.addFlashAttribute("success", m);
        } catch(UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Job wurde nicht gefunden.");
        }
        return "redirect:/company/jobs";
    }

    @PostMapping("/jobs/{id}/accept")
    public String acceptApplicant(@PathVariable("id") Long id, @RequestParam("username") String applicantUsername, RedirectAttributes redirectAttributes) {
        try {
            Job job = jobService.getJobWithApplicants(id)
                    .orElseThrow(() -> new JobNotFoundException("job not found."));
            Customer applicant = customerService.getCustomerWithApplications(applicantUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("customer not found."));
            // if the customer does already have a job (currentJob) he will get accepted as the worker of the job
            if(customerService.CustomerHasJob(applicant)) {
                redirectAttributes.addFlashAttribute("error", "Bewerber hat bereits einen Job.");
                // I the customer already has a job he will be removed as an applicant
                customerService.removeApplication(job, applicant);
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
    public String denyApplicant(@PathVariable("id") Long id, @RequestParam("username") String applicantUsername, RedirectAttributes redirectAttributes) {
        try {
            Job job = jobService.getJobWithApplicants(id)
                    .orElseThrow(() -> new JobNotFoundException("job not found."));
            Customer applicant = customerService.getCustomerWithApplications(applicantUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("customer not found"));
            customerService.removeApplication(job, applicant);
            redirectAttributes.addFlashAttribute("success", "Bewerber wurde abgelehnt");
        } catch(UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Job wurde nicht gefunden.");
        }
        return "redirect:/company/jobs";
    }
}
