package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Service.CompanyService;
import de.thb.MACJEE.Service.CustomerService;
import de.thb.MACJEE.Service.JobFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private JobFinder jobFinder;

    @GetMapping("/customer/{id}")
    public String showCustomerProfile (@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("customer", customer);
        return "customerProfile";
    }

    @PostMapping("/customer/{id}/find-jobs")
    public String findJobsByCompany(@PathVariable("id") Long id, Model model) {
        try {
            Customer customer = customerService.getCustomerById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            Long customerId = customer.getId();
            List<Job> jobs = jobFinder.findPerfectJobs(customerId);
            model.addAttribute("jobs", jobs);
            model.addAttribute("customer", customer);
            return "jobsList";
        } catch (Exception e) {
            // Handle the exception appropriately
            // You can log the error or show a custom error page
            return "error";
        }
    }

}