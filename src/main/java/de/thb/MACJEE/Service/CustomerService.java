package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Role;
import de.thb.MACJEE.Repository.CustomerRepository;
import lombok.Data;
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
import java.util.Optional;
import java.util.stream.Collectors;

import de.thb.MACJEE.Entitys.Customer;



@Service
@Data
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found!"));
        return new User(customer.getUsername(), customer.getPassword(), mapRolesToAuthorities(customer.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }*/

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerByUserName(String userName) throws UsernameNotFoundException {
        return customerRepository.findCustomerByUsername(userName);
    }
}
