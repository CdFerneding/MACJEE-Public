package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Job.class, idClass = Long.class)
public interface jobRepository extends CrudRepository<Job, Long> {
}
