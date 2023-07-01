package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Controller.form.CustomerSettingsForm;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Enumerations.Characteristics;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Service.CustomerService;
import de.thb.MACJEE.Service.JobFinder;
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

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/customer")
@Data
public class CustomerController {

    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final JobFinder jobFinder;

    @GetMapping("/profile")
    public String showCustomerProfile (Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Customer customer = customerService.getCustomerByUsernameWithSkills(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found."));
        List<Job> applications = customerService.getApplicationsOfCustomer(customer.getId());
        model.addAttribute("customer", customer);
        model.addAttribute("applications", applications);
        return "user/customerProfile";
    }

    @GetMapping("/customerSettings")
    public String showCustomerSettings(@RequestParam("changes") String changes, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.getCustomerByUserName(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("characteristics", Characteristics.values());
        model.addAttribute("changes", changes);
        model.addAttribute("customer", customer);
        return "user/customerSettings";
    }

    @PostMapping("/customerSettings")
    public String postCustomerSettings(@RequestParam("changes") String changes, CustomerSettingsForm customerSettingsForm) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.getCustomerByUserName(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        customerService.Settings(customer, changes, customerSettingsForm);
        return "redirect:/customer/profile";
    }

    @GetMapping("/job/perfect")
    public String showPerfectJob(Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try{
            // fetch customer with his skills
            Customer customer = customerService.getCustomerByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));

            List<Job> jobs = jobFinder.getPerfectJobs(customer);
            if (jobs != null) {
                model.addAttribute("jobs", jobs);
                model.addAttribute("success", "Es wurde ein perfekter Job für dich gefunden.");
            } else {
                model.addAttribute("error", "Es wurde KEIN perfekter Job für dich gefunden. ");
                return "/user/dashboard";
            }
        } catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "there is a problem with the currently logged in user.");
        }
        return "job/perfect";
    }
}