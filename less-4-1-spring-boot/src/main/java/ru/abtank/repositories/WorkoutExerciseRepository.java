package ru.abtank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.abtank.entities.WorkoutExercise;
import ru.abtank.entities.WorkoutExerciseId;

import java.util.Optional;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise , WorkoutExerciseId>, JpaSpecificationExecutor<WorkoutExercise> {

}
