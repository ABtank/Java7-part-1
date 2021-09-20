package ru.abtank.servises;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.dto.ExerciseDto;
import ru.abtank.persist.entities.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    List<ExerciseDto> findAllDto();
    Optional<ExerciseDto> findByIdDto(Integer id);
    List<Exercise> findAll();
    Optional<Exercise> findById(Integer id);
    void deleteById(Integer id);
    List<Exercise>findAll(Specification<Exercise> spec);
    void save(Exercise exercise);
    Exercise saveOrUpdate(Exercise exercise);
}
