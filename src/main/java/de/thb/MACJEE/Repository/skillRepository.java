package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Skill.class, idClass = Long.class)
public interface skillRepository extends CrudRepository<Skill, Long> {
}
