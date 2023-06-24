package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import org.springframework.data.jpa.repository.Query;
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
    @Query("SELECT CASE WHEN (COUNT(c) > 0) THEN true ELSE false END FROM Customer c WHERE c = :customer AND c.currentJob IS NOT NULL")
    boolean customerHasCurrentJob(Customer customer);

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.applications WHERE c.username = :username")
    Optional<Customer> findCustomerWithApplications(String username);
}