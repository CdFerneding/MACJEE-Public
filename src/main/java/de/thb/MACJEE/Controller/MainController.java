package de.thb.MACJEE.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Data
public class MainController {

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
    public String showDashboard() {
        return "user/dashboard";
    }

    @GetMapping("/error")
    public String showErrorPage(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("status", status);
        return "/error";
    }
}
