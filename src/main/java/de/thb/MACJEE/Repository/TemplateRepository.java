package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Job.class, idClass = Long.class)
public interface TemplateRepository extends CrudRepository<Template, Long> {

}
