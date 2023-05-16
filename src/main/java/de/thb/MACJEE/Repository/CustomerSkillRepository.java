package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.CustomerSkill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = CustomerSkill.class, idClass = Long.class)
public interface CustomerSkillRepository extends CrudRepository<CustomerSkill, Long> {
}
