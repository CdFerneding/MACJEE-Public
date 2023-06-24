package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Controller.form.RegisterCompanyForm;
import de.thb.MACJEE.Controller.form.RegisterCustomerForm;
import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Exeption.JobNotFoundException;
import de.thb.MACJEE.Service.CompanyService;
import de.thb.MACJEE.Service.CustomerService;
import de.thb.MACJEE.Service.JobService;
import jakarta.transaction.Transactional;
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
        Company company = companyService.getCompanyByCompanyName(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("company", company);
        return "user/companyProfile";
    }

    @GetMapping("/companySettings")
    public String showCompanySettings(@RequestParam("changes") String changes, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Company company = companyService.getCompanyByCompanyName(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("changes", changes);
        model.addAttribute("company", company);
        return "user/companySettings";
    }

    @PostMapping("/companySettings")
    public String postCompanySettings(@RequestParam("changes") String changes, RegisterCompanyForm registerCompanyForm, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Company company = companyService.getCompanyByCompanyName(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (changes.equals("mail")) company.setMail(registerCompanyForm.getMail());
        if (changes.equals("phoneNumber")) company.setPhoneNumber(registerCompanyForm.getPhoneNumber());
        if (changes.equals("website")) company.setWebsite(registerCompanyForm.getWebsite());
        if (changes.equals("address1")) company.setAddress1(registerCompanyForm.getAddress1());
        if (changes.equals("address2")) company.setAddress2(registerCompanyForm.getAddress2());
        if (changes.equals("country")) company.setCountry(registerCompanyForm.getCountry());
        if (changes.equals("state")) company.setState(registerCompanyForm.getState());
        if (changes.equals("zip")) company.setZip(registerCompanyForm.getZip());
        if (changes.equals("name")) {
            company.setMail(registerCompanyForm.getFirstName());
            company.setMail(registerCompanyForm.getLastName());
        }

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
                    .orElseThrow(() -> new JobNotFoundException("job not found."));
            List<Customer> applicants = jobService.getApplicantsOfJob(job.getId());

            model.addAttribute("applicants", applicants);
            model.addAttribute("job", job);
            model.addAttribute("company", company);
        } catch(UsernameNotFoundException e) {
            model.addAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            model.addAttribute("error", "Job wurde nicht gefunden.");
        }
        return "job/applicants";
    }

    @PostMapping("/jobs/{id}/accept")
    public String acceptApplicant(@PathVariable("id") Long id, @RequestParam("username") String applicantUsername,
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
            // send the applicant a message...
            jobService.setAvailabilityStateOfJob(false, job);
            redirectAttributes.addFlashAttribute("success", "Bewerber wurde angenommen");
        } catch(UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "derzeit angemeldete Firma nicht gefunden.");
        } catch(JobNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Job wurde nicht gefunden.");
        }
        return "redirect:/company/jobs";
    }

    @Transactional // for loading the job.applicants
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
