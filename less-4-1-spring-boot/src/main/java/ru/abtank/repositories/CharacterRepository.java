package ru.abtank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.entities.Character;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character ,Integer>, JpaSpecificationExecutor<Character> {

    Optional<Character> findByCharacter(String character);
    Optional<Character> findByCharacterLike(String characterPattern);
}
