package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.CustomerSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = CustomerSkill.class, idClass = Long.class)
public interface CustomerSkillRepository extends JpaRepository<CustomerSkill, Long> {
}
