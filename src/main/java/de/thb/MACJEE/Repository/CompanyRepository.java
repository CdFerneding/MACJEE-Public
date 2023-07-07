package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Company.class, idClass = Long.class)
public interface CompanyRepository extends CrudRepository<Company, Long> {
    @Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.jobs WHERE c.username = :username")
    Optional<Company> findCompanyByUsernameWithJobs(String username);

    Optional<Company> findCompanyByUsername(String username);

    Optional<Company> findByUsername(String username);
    boolean existsByCompanyName(String companyName);
}
