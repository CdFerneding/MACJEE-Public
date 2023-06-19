package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Service.CompanyService;
import de.thb.MACJEE.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;

    @GetMapping("/jobs")
    public String showAllOpenJobs(Model model) {
        List<Job> jobs = jobService.getOpenJobs();
        model.addAttribute("jobs", jobs);
        return "user/openJobs";
    }
}
