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

    @Query("SELECT j FROM Job j WHERE j.requiredSkills IN :requiredSkills AND j.isTemplate = false")
    List<Job> findBySkillsIn(@Param("requiredSkills") List<Skill> requiredSkills);

    @Query("SELECT DISTINCT j FROM Job j LEFT JOIN FETCH j.requiredSkills WHERE j.isOpen = :isOpen AND j.isTemplate = false")
    List<Job> findJobsByIsOpen(boolean isOpen);

    @Query("SELECT DISTINCT j FROM Job j LEFT JOIN FETCH j.applicants WHERE j.id = :jobId AND j.isTemplate = false")
    Optional<Job> findJobByIdWithApplicants(Long jobId);

    @Query("SELECT j.company FROM Job j WHERE j.id = :jobId AND j.isTemplate = false")
    Optional<Company> findCompanyByJobId(@Param("jobId") Long jobId);

    @Query("SELECT DISTINCT c FROM Job j LEFT JOIN j.applicants c LEFT JOIN FETCH c.skills WHERE j.id = :jobId AND j.isTemplate = false")
    List<Customer> findApplicantsWithSkillsByJobId(@Param("jobId") Long jobId);

    @Query("SELECT DISTINCT j FROM Job j WHERE j.company = :company")
    Optional<Job> findJobByCompany(Company company);

    @Query("SELECT DISTINCT j FROM Job j LEFT JOIN FETCH j.applicants WHERE j.company = :company AND j.isTemplate = false")
    List<Job> findJobsByCompanyWithApplicants(Company company);

    @Query("SELECT DISTINCT j FROM Job j LEFT JOIN FETCH j.applicants WHERE j.id = :id AND j.isTemplate = false")
    Optional<Job> findJobWithApplicants(Long id);

    @Query("SELECT DISTINCT j FROM Job j LEFT JOIN FETCH j.requiredSkills WHERE j.id = :id AND j.isTemplate = false")
    Optional<Job> findJobWithRequiredSkills(Long id);

}
