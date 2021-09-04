package ru.abtank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.entities.Set;

import java.util.Optional;

@Repository
public interface SetRepository extends JpaRepository<Set ,Integer>, JpaSpecificationExecutor<Set> {


}
