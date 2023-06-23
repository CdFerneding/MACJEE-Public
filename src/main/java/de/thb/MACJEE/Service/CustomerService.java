package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Repository.CustomerRepository;
import de.thb.MACJEE.Repository.JobRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import de.thb.MACJEE.Entitys.Customer;

import javax.swing.text.html.Option;


@Service
@Data
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JobRepository jobRepository;

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
    public void setCustomerWorkingAt(Customer customer, Job job) {
        customer.setCurrentJob(job);
        job.setWorking(customer);
        customerRepository.save(customer);
        jobRepository.save(job);
    }
    public boolean CustomerHasJob(Customer customer) {
        return customerRepository.customerHasCurrentJob(customer);
    }
    public void removeApplication(Job job, Customer customer) {
        List<Customer> applicants = job.getApplicants();
        applicants.remove(customer);
        List<Job> applications = customer.getApplications();
        applications.remove(job);
        customerRepository.save(customer);
        jobRepository.save(job);
    }
    public Optional<Customer> getCustomerWithApplications(String username) {
        return customerRepository.findCustomerWithApplications(username);
    }
}
