package ru.abtank.servises;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.abtank.dto.ExerciseDto;
import ru.abtank.persist.entities.Exercise;
import ru.abtank.persist.repositories.ExerciseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private ExerciseRepository exerciseRepository;

    @Autowired
    public void setExerciseRepository(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<ExerciseDto> findAllDto() {
        return exerciseRepository.findAll().stream().map(ExerciseDto::new).collect(Collectors.toList());
    }

    @Override
    public Optional<ExerciseDto> findByIdDto(Integer id) {
        return exerciseRepository.findById(id).map(ExerciseDto::new);
    }

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @Override
    public Optional<Exercise> findById(Integer id) {
        return exerciseRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        exerciseRepository.deleteById(id);
    }

    @Override
    public List<Exercise> findAll(Specification<Exercise> spec) {
        return exerciseRepository.findAll(spec);
    }

    @Override
    public void save(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    @Override
    public Exercise saveOrUpdate(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
}
