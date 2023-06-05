package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Repository.CompanyRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Optional<Company> getCompanyByCompanyName(String companyName) {
        return companyRepository.findCompanyByUserName(companyName);
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

}
