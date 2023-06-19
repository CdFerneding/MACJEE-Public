package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;


@RepositoryDefinition(domainClass = Company.class, idClass = Long.class)
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Optional<Company> findCompanyByUsername(String companyName);

    Optional<Company> findByUsername(String username);
    boolean existsByCompanyName(String companyName);
}
