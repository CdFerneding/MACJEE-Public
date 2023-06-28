package de.thb.MACJEE.Service;

import de.thb.MACJEE.Controller.form.CompanySettingsForm;
import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Enumerations.Characteristics;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Entitys.Skill;
import de.thb.MACJEE.Repository.CompanyRepository;
import de.thb.MACJEE.Repository.JobRepository;
import de.thb.MACJEE.Repository.SkillRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private SkillRepository skillRepository;

    public Optional<Company> getCompanyByCompanyName(String companyName) {
        return companyRepository.findCompanyByUsernameWithJobs(companyName);
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Company company = companyRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found!"));
        return new User(company.getUsername(), company.getPassword(), mapRolesToAuthorities(company.getRoles()));
    }
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    } */

    public Optional<Company> getCompanyByUserName(String username) {
        return companyRepository.findCompanyByUsername(username);
    }

    public List<Job> getJobsByCompany(Company company) {
        return jobRepository.findJobsByCompanyWithApplicants(company);
    }

    public void setAttributes(Company company, String changes, CompanySettingsForm companySettingsForm){
        switch (changes) {
            case "description" -> {
                company.setDescription(companySettingsForm.getDescription());
                companyRepository.save(company);
            }
            case "mail" -> {
                company.setMail(companySettingsForm.getMail());
                companyRepository.save(company);
            }
            case "phoneNumber" -> {
                company.setPhoneNumber(companySettingsForm.getPhoneNumber());
                companyRepository.save(company);
            }
            case "website" -> {
                company.setWebsite(companySettingsForm.getWebsite());
                companyRepository.save(company);
            }
            case "address1" -> {
                company.setAddress1(companySettingsForm.getAddress1());
                companyRepository.save(company);
            }
            case "address2" -> {
                company.setAddress2(companySettingsForm.getAddress2());
                companyRepository.save(company);
            }
            case "country" -> {
                company.setCountry(companySettingsForm.getCountry());
                companyRepository.save(company);
            }
            case "state" -> {
                company.setState(companySettingsForm.getState());
                companyRepository.save(company);
            }
            case "zip" -> {
                company.setZip(companySettingsForm.getZip());
                companyRepository.save(company);
            }
            case "name" -> {
                company.setFirstName(companySettingsForm.getFirstName());
                company.setLastName(companySettingsForm.getLastName());
                companyRepository.save(company);
            }
            case "newJob" -> {
                Job job = new Job();
                job.setTitle(companySettingsForm.getJobTitle());
                job.setSalary(companySettingsForm.getJobSalary());
                job.setDescription(companySettingsForm.getJobDescription());
                job.setCompany(company);
                job.setOpen(true);
                company.addJob(job);
                jobRepository.save(job);
                List<Skill> skills = new ArrayList<Skill>();
                int i = 0;
                for(Characteristics skillName : Characteristics.values()) {
                    Skill skill = new Skill();
                    skill.setName(skillName.toString());
                    skill.setLevel(companySettingsForm.getSkillValue().get(i));
                    skill.setIsHardSkill(companySettingsForm.getSkillHard().get(i));
                    List<Job> jobs = new ArrayList<>(Collections.singletonList(job));
                    skill.setJobs(jobs);
                    skillRepository.save(skill);
                    skills.add(skill);
                    i++;
                }
                job.setRequiredSkills(skills);
                jobRepository.save(job);
                companyRepository.save(company);
            }
        }
    }
}
