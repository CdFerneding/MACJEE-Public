package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Controller.form.RegisterCompanyForm;
import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Role;
import de.thb.MACJEE.Exeption.UserRoleNotFoundException;
import de.thb.MACJEE.Controller.form.RegisterCustomerForm;
import de.thb.MACJEE.Service.CompanyService;
import de.thb.MACJEE.Service.CustomerService;
import de.thb.MACJEE.Service.UserEntityService;
import de.thb.MACJEE.Service.RoleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Controller
@RequestMapping("/auth")
@Data
public class AuthController {

    @Autowired
    private final UserEntityService userEntityService;
    @Autowired
    private final RoleService roleService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final CompanyService companyService;

    /*@GetMapping("/login")
    public String showLogin() {
        return "login";
    }*/

    @GetMapping("/register")
    public String showRegisterForm() {
        return "auth/choose_customer_company";
    }

    @GetMapping("/registerCustomer")
    public String showCustomerRegistration() {
        return "auth/register_customer";
    }

    @GetMapping("/registerCompany")
    public String showCompanyRegistration() {
        return "auth/register_company";
    }

    @PostMapping("/registerCustomer")
    public String registerCustomer(RegisterCustomerForm registerCustomerForm, Model model) {
        Role userRole = roleService.getRoleByName("ROLE_CUSTOMER").orElseThrow(() -> new UserRoleNotFoundException("User role not found"));
        String username = registerCustomerForm.getUsername();
        String userPassword = registerCustomerForm.getPassword();

        if (userEntityService.existsByUsername(username)) {
            model.addAttribute("error", "Username is taken!");
            return "auth/register_customer"; // Return back to the register page with an error message
        }

        String dateOfBirthString = registerCustomerForm.getDoB();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth;
        try {
            dateOfBirth = dateFormat.parse(dateOfBirthString);
        } catch (ParseException e) {
            model.addAttribute("error", "Invalid date format");
            return "auth/register_customer";
        }

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(passwordEncoder.encode(userPassword));
        customer.setFirstName(registerCustomerForm.getFirstName());
        customer.setLastName(registerCustomerForm.getLastName());
        customer.setMail(registerCustomerForm.getMail());
        customer.setDoB(dateOfBirth);
        customer.setRoles(Collections.singletonList(userRole));

        customerService.addCustomer(customer);

        model.addAttribute("success", "User registered successfully!");
        return "login"; // Return back to the login page with a success message
    }

    @PostMapping("/registerCompany")
    public String registerCompany(RegisterCompanyForm registerCompanyForm, Model model) {
        Role userRole = roleService.getRoleByName("ROLE_COMPANY").orElseThrow(() -> new UserRoleNotFoundException("User role not found"));
        String username = registerCompanyForm.getUsername();
        String userPassword = registerCompanyForm.getPassword();

        if (userEntityService.existsByUsername(username)) {
            model.addAttribute("error", "Username is taken!");
            return "auth/register_company"; // Return back to the register page with an error message
        }

        Company company = new Company();
        company.setUsername(username);
        company.setPassword(passwordEncoder.encode(userPassword));
        company.setCompanyName(registerCompanyForm.getCompanyName());
        company.setFirstName(registerCompanyForm.getFirstName());
        company.setLastName(registerCompanyForm.getLastName());
        company.setMail(registerCompanyForm.getMail());
        company.setDescription(registerCompanyForm.getDescription());
        company.setAddress1(registerCompanyForm.getAddress1());
        company.setAddress2(registerCompanyForm.getAddress2());
        company.setCountry(registerCompanyForm.getCountry());
        company.setState(registerCompanyForm.getState());
        company.setZip(registerCompanyForm.getZip());
        company.setWebsite(registerCompanyForm.getWebsite());
        company.setRoles(Collections.singletonList(userRole));

        companyService.addCompany(company);

        model.addAttribute("success", "User registered successfully!");
        return "login"; // Return back to the login page with a success message
    }
}
