package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Customer.class, idClass = Long.class)
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findCustomerByUsername(String username);
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findCustomerById(Long id);

    //Optional<Long> findIdByCustomer(Customer customer);
    boolean existsByUsername(String userName);
}