package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@RepositoryDefinition(domainClass = Customer.class, idClass = Long.class)
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCompanyByUserName(String userName);
    boolean existsByUserName(String userName);
}
