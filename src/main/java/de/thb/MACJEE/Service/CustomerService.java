package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Repository.CustomerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Job> getApplicationsOfCustomer(Long customerId) {
        return customerRepository.findApplicationsOfCustomer(customerId);
    }

    public Optional<Customer> getCustomerByUserName(String userName) throws UsernameNotFoundException {
        return customerRepository.findCustomerByUsername(userName);
    }
}
