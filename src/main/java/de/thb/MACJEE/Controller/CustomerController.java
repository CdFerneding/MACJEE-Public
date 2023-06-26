package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Controller.form.CustomerSettingsForm;
import de.thb.MACJEE.Entitys.Customer;
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
        Customer customer = customerService.getCustomerByUserName(username)
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
        model.addAttribute("changes", changes);
        model.addAttribute("customer", customer);
        return "user/customerSettings";
    }

    @PostMapping("/customerSettings")
    public String postCustomerSettings(@RequestParam("changes") String changes, CustomerSettingsForm customerSettingsForm, Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.getCustomerByUserName(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        customerService.Settings(customer, changes, customerSettingsForm);

        model.addAttribute("customer", customer);
        return "user/customerProfile";
    }

    @PostMapping("/{id}/find-jobs")
    public String findJobsByCompany(@PathVariable("id") Long id, Model model) {
        try {
            Customer customer = customerService.getCustomerById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            Long customerId = customer.getId();
            List<Job> jobs = jobFinder.findPerfectJobs(customerId);
            model.addAttribute("jobs", jobs);
            model.addAttribute("customer", customer);
            return "job/findJob";
        } catch (Exception e) {
            // Handle the exception appropriately
            // You can log the error or show a custom error page
            return "error";
        }
    }

}