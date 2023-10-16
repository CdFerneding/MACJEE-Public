package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Service.CompanyService;
import de.thb.MACJEE.Service.CustomerService;
import de.thb.MACJEE.Service.JobService;
import de.thb.MACJEE.Service.RoleService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@Data
public class MainController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobService jobService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public String showHome(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Clearing the Context Holder
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            model.addAttribute("success", "Logout erfolgreich");
        } else {
            model.addAttribute("error", "Logout fehlgeschlagen");
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if(authentication.getAuthorities().toString().contains(roleService.getRoleByName("ROLE_CUSTOMER").get().getName())){
            String username = authentication.getName();
            Customer customer = customerService.getCustomerByUsernameWithSkills(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username not found."));
            model.addAttribute("customer", customer);

        }
        else if(authentication.getAuthorities().toString().contains(roleService.getRoleByName("ROLE_COMPANY").get().getName())){
            Company company = companyService.getCompanyByUsername(authentication.getName())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            model.addAttribute("company", company);

        }



        return "user/dashboard";
    }

    @GetMapping("/error")
    public String showErrorPage(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("status", status);
        return "/error";
    }
}
