package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Job.class, idClass = Long.class)
public interface JobRepository extends CrudRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE j.requiredSkills IN :requiredSkills")
    List<Job> findBySkillsIn(@Param("requiredSkills") List<Skill> requiredSkills);

    List<Job> findJobsByIsOpen(boolean isOpen);

    Optional<Job> findJobById(Long jobId);

    @Query("SELECT j.company FROM Job j WHERE j.id = :jobId")
    Optional<Company> findCompanyByJobId(@Param("jobId") Long jobId);

    @Query("SELECT j.applicants FROM Job j WHERE j.id = :jobId")
    List<Customer> findApplicantsByJobId(@Param("jobId") Long jobId);

    Optional<Job> findJobByCompany(Company company);

    List<Job> findJobsByCompany(Company company);
}
