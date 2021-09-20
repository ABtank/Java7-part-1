package ru.abtank.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.entities.Set;

@Repository
public interface SetRepository extends JpaRepository<Set ,Integer>, JpaSpecificationExecutor<Set> {


}
