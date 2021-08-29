package ru.abtank.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.entity.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role ,Integer>, JpaSpecificationExecutor<Role> {

    Optional<Role> findByName(String name);
    Optional<Role> findByNameLike(String namePattern);
}
