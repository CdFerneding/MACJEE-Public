package de.thb.MACJEE.Service;

import de.thb.MACJEE.Controller.form.RegisterCompanyForm;
import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Repository.CompanyRepository;
import de.thb.MACJEE.Repository.JobRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobRepository jobRepository;

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

    public void setAttributes(Company company, String changes, RegisterCompanyForm registerCompanyForm){
        switch (changes) {
            case "description" -> {
                company.setDescription(registerCompanyForm.getDescription());
                companyRepository.save(company);
            }
            case "mail" -> {
                company.setMail(registerCompanyForm.getMail());
                companyRepository.save(company);
            }
            case "phoneNumber" -> {
                company.setPhoneNumber(registerCompanyForm.getPhoneNumber());
                companyRepository.save(company);
            }
            case "website" -> {
                company.setWebsite(registerCompanyForm.getWebsite());
                companyRepository.save(company);
            }
            case "address1" -> {
                company.setAddress1(registerCompanyForm.getAddress1());
                companyRepository.save(company);
            }
            case "address2" -> {
                company.setAddress2(registerCompanyForm.getAddress2());
                companyRepository.save(company);
            }
            case "country" -> {
                company.setCountry(registerCompanyForm.getCountry());
                companyRepository.save(company);
            }
            case "state" -> {
                company.setState(registerCompanyForm.getState());
                companyRepository.save(company);
            }
            case "zip" -> {
                company.setZip(registerCompanyForm.getZip());
                companyRepository.save(company);
            }
            case "name" -> {
                company.setFirstName(registerCompanyForm.getFirstName());
                company.setLastName(registerCompanyForm.getLastName());
                companyRepository.save(company);
            }
        }
    }
}
