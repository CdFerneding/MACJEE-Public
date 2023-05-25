package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;


@RepositoryDefinition(domainClass = Company.class, idClass = Long.class)
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyName(String companyName);
    boolean existsByCompanyName(String companyName);
}
