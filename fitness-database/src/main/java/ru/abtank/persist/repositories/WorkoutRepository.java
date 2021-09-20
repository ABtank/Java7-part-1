package ru.abtank.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.entities.Workout;

import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout ,Integer>, JpaSpecificationExecutor<Workout> {

    Optional<Workout> findByName(String workout);
    Optional<Workout> findByNameLike(String workoutPattern);
}
