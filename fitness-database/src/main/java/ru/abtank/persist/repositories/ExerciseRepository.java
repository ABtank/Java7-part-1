package ru.abtank.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.dto.ExerciseDto;
import ru.abtank.persist.entities.Exercise;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise ,Integer>, JpaSpecificationExecutor<Exercise> {

//    @Query("select e.id, e.name, e.descr, e.creator.login, e.isCardio, e.character.name, e.category.name, e.cardioName1, e.cardioName2, e.cardioName3 from Exercise e")
    @Query("select new ru.abtank.persist.dto.ExerciseDto(e) from Exercise e")
    List<ExerciseDto> findAllExercise();


    Optional<Exercise> findByName(String exercise);
    Optional<Exercise> findByNameLike(String exercisePattern);
}
