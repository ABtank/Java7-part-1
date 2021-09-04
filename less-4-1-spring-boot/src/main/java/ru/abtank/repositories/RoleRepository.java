package ru.abtank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.entities.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role ,Integer>, JpaSpecificationExecutor<Role> {

    Optional<Role> findByName(String name);
    Optional<Role> findByNameLike(String namePattern);
}
