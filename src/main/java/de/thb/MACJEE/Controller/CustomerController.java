package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Service.CustomerService;
import de.thb.MACJEE.Service.JobFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private JobFinder jobFinder;

    @GetMapping("/")
    public String showCustomerProfile (Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) {
            model.addAttribute("error", "You are not allowed to access this site!");
            return "error";
        }
        Customer customer = customerService.getCustomerByUserName(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("username not found."));
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
            return "user/findJob";
        } catch (Exception e) {
            // Handle the exception appropriately
            // You can log the error or show a custom error page
            return "error";
        }
    }

}