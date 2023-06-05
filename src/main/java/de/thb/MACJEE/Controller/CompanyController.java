package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @GetMapping("/company/{id}")
    public String showCompanyProfile(@PathVariable("id") Long id, Model model) {
        Company company = companyService.getCompanyById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("company", company);
        return "companyProfile";
    }

}
