package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.CustomerVO;
import de.thb.MACJEE.Entitys.Role;
import jakarta.servlet.http.HttpServlet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class LoginController {

    public LoginController(){
        super();
    }


    /**
     * Registrierungsformular Arbeitnehmer
     * @param model
     * @return
     */
    @GetMapping("/registration_an")
    public String showRegistrationAn(Model model){
        /*model.addAttribute("notebooks", deskService.getAllNotebooks());
        model.addAttribute("formNotebook", new Notebook());*/
        return "registration_an";
    }

    /**
     * Registrierungsformular Arbeitgeber
     * @param model
     * @return
     */
    @GetMapping("/registration_ag")
    public String showRegistrationAg(Model model){
        model.addAttribute("customer", new CustomerVO());
        return "registration_ag";
    }

    @PostMapping("/registration_ag")
    public String getRegistrationAg(@ModelAttribute CustomerVO customer){
        System.out.println(customer.toString());
        return "redirect:/index_2";
    }

    /**
     * Registrierungsformular zur Determination der USER_ROLE --- Get request
     * @param model
     * @return
     */
    @GetMapping("/registration_zero")
    public String showRegistrationZero(Model model){
        model.addAttribute("role", new Role());
        return "registration_zero";
    }

    /**
     * Registrierungsformular zur Determination der USER_ROLE --- Post request
     * @param role
     * @return
     */
    @PostMapping("/registration_zero")
    public String getRegistrationZero(@ModelAttribute Role role){
        if(role.role == 2){
            //System.out.println("Test");
            return "redirect:/registration_ag";
        }
        else if(role.role == 1) return "registration_an";
        return "redirect:/registration_zero";
    }

    /**
     * Anmeldungsformular mit input Benutzername und PW
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String showLogin(Model model){
        /*model.addAttribute("notebooks", deskService.getAllNotebooks());
        model.addAttribute("formNotebook", new Notebook());*/
        return "login";
    }

}
