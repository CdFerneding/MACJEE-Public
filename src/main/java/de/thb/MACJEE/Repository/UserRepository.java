package de.thb.MACJEE.Repository;

import de.thb.MACJEE.Entitys.Company;
import de.thb.MACJEE.Entitys.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;


@RepositoryDefinition(domainClass = UserEntity.class, idClass = Long.class)
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);

}
