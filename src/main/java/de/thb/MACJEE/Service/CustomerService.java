package de.thb.MACJEE.Service;

import de.thb.MACJEE.Repository.CustomerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import de.thb.MACJEE.Entitys.Customer;



@Service
@Data
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }

    public Optional<Customer> getCustomerByUserName(String userName) {
        return customerRepository.findCustomerByUserName(userName);
    }

}
