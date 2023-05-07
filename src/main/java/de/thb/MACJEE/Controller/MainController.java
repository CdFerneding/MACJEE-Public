package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Role;
import jakarta.servlet.http.HttpServlet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController extends HttpServlet {


    public MainController(){
        super();
    }

    /**
     * Startseite mit mgl. zur Anmeldung und Registrierung
     * @param model
     */
    @GetMapping("/index")
    public void showIndex(Model model){
        /*model.addAttribute("notebooks", deskService.getAllNotebooks());
        model.addAttribute("formNotebook", new Notebook());*/
        //return "index";
    }

    /**
     * Main Startseite mit mgl. zur Anmeldung und Registrierung
     * @param model
     */
    @GetMapping("/index_2")
    public void showIndex2(Model model){
        /*model.addAttribute("notebooks", deskService.getAllNotebooks());
        model.addAttribute("formNotebook", new Notebook());*/
        //return "index_2";
    }


}

