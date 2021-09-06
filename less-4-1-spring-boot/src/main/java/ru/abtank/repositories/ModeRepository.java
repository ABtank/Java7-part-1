package ru.abtank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.entities.Mode;

import java.util.Optional;

@Repository
public interface ModeRepository extends JpaRepository<Mode ,Integer>, JpaSpecificationExecutor<Mode> {

    Optional<Mode> findByName(String mode);
    Optional<Mode> findByNameLike(String modePattern);
}
