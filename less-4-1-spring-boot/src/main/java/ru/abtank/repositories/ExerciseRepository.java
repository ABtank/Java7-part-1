package ru.abtank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.dto.ExerciseDto;
import ru.abtank.entities.Exercise;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise ,Integer>, JpaSpecificationExecutor<Exercise> {

//    Optional<ExerciseDto> findByExerciseId (Integer id);

    Optional<Exercise> findByName(String exercise);
    Optional<Exercise> findByNameLike(String exercisePattern);
}
