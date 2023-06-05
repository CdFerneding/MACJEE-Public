package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Role.class, idClass = Long.class)
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName (String name);
}
