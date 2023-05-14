package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Company.class, idClass = Long.class)
public interface companyRepository extends CrudRepository<Company, Long> {
}
