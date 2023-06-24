package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Customer;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Entitys.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Customer.class, idClass = Long.class)
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("SELECT DISTINCT c FROM Customer c " +
            "LEFT JOIN FETCH c.skills " +
            "WHERE c.username = :username")
    Optional<Customer> findCustomerByUsername(String username);
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findCustomerById(Long id);

    //Optional<Long> findIdByCustomer(Customer customer);
    boolean existsByUsername(String userName);
    @Query("SELECT DISTINCT j FROM Customer c " +
            "JOIN c.applications j " +
            "WHERE c.id = :customerId")
    List<Job> findApplicationsOfCustomer(Long customerId);
}