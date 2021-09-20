package ru.abtank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.dto.ExerciseDto;
import ru.abtank.persist.entities.Exercise;
import ru.abtank.servises.ExerciseService;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("api/v1/exercise")
public class RestExerciseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RestExerciseController.class);

    private ExerciseService exerciseService;

    @Autowired
    public void setExerciseService(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<ExerciseDto> getAllExercise() {
        return exerciseService.findAllDto();
    }

    @GetMapping ("/{id}")
    public ExerciseDto getExerciseDto(@PathVariable Integer id) {
        return exerciseService.findByIdDto(id).orElseThrow(() ->  new IllegalArgumentException("exerciseDto Id not found"));
    }

    @PostMapping
    public ExerciseDto createExercise(@RequestBody ExerciseDto exerciseDto) {
        exerciseDto.setId(null);
        LOGGER.info("createExercise{}",exerciseDto);
        return exerciseDto;
//        return exerciseService.saveOrUpdate(exercise);
    }

    @PutMapping
    public ExerciseDto updateExercise(@RequestBody ExerciseDto exerciseDto) {
        LOGGER.info("createExercise{}",exerciseDto);
        return exerciseDto;
//        return exerciseService.saveOrUpdate(exercise);
    }

    @DeleteMapping
    public void deleteAllExercise() {
        LOGGER.info("deleteAllExercise");
    }

    @DeleteMapping("/{id}")
    public void deleteAllExercise(@PathVariable Integer id) {
        LOGGER.info("deleteAllExercise");
        exerciseService.deleteById(id);
    }



    @ExceptionHandler
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<String> constraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
