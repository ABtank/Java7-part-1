package ru.abtank.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.entities.Character;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character ,Integer>, JpaSpecificationExecutor<Character> {

    Optional<Character> findByName(String character);
    Optional<Character> findByNameLike(String characterPattern);
}
