package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.Role;
import de.thb.MACJEE.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService implements UserDetailsService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String companyName) throws UsernameNotFoundException {
        Company company = companyRepository.findByCompanyName(companyName).orElseThrow(() -> new UsernameNotFoundException("Company-name not found"));
        return new User(company.getCompanyName(), ""+company.getPassword(), mapRoleToAuthorities(company.getRoles()));
    }

    private Collection<GrantedAuthority> mapRoleToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}
