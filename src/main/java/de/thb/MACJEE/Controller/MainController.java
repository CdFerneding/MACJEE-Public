package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Role;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {


    public MainController(){
        super();
    }

    @GetMapping("/")
    public String showHome(Model model) {
        return "index";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboardTest";
    }

    @GetMapping("/error")
    public String showErrorPage(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("status", status);
        return "/error";
    }
}
