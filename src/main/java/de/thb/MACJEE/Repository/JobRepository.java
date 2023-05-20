package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Job.class, idClass = Long.class)
public interface JobRepository extends JpaRepository<Job, Long> {
}
