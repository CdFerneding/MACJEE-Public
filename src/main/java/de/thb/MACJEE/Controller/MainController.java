package de.thb.MACJEE.Controller;

import jakarta.servlet.http.HttpServlet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController extends HttpServlet {
    /*
    @Serial
    private static final long serialVersionUID = 1L;*/

    public MainController(){
        super();
    }

    /*
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Hallo Welt\r\n");
    }*/

    @GetMapping("/index")
    public void showIndex(Model model){
        /*model.addAttribute("notebooks", deskService.getAllNotebooks());
        model.addAttribute("formNotebook", new Notebook());*/
        //return "index";
    }

    @GetMapping("/registration_an")
    public String showRegistrationAn(Model model){
        /*model.addAttribute("notebooks", deskService.getAllNotebooks());
        model.addAttribute("formNotebook", new Notebook());*/
        return "registration_an";
    }

    @GetMapping("/registration_ag")
    public String showRegistrationAg(Model model){
        /*model.addAttribute("notebooks", deskService.getAllNotebooks());
        model.addAttribute("formNotebook", new Notebook());*/
        return "registration_ag";
    }

    @GetMapping("/registration_zero")
    public String showRegistrationZero(Model model){
        return "registration_zero";
    }

    @PostMapping("/registration_zero")
    public String getRegistrationZero(@PathVariable("role") int role, Model model){
        if(role == 1) return "redirect:/registration_ag";
        else if(role == 2) return "registration_an";
        return "registration_zero";
    }

    @GetMapping("/login")
    public String showLogin(Model model){
        /*model.addAttribute("notebooks", deskService.getAllNotebooks());
        model.addAttribute("formNotebook", new Notebook());*/
        return "login";
    }


}

