package ru.abtank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.abtank.dto.ExerciseDto;
import ru.abtank.entities.Exercise;
import ru.abtank.repositories.ExerciseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {
    private ExerciseRepository exerciseRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public void setExerciseRepository(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

//    @GetMapping
//    @ResponseBody
//    public ExerciseDto exerciseAll(){
//        ExerciseDto exerciseList = exerciseRepository.findByExerciseId(1).get();
//        System.out.println("///////////////////"+exerciseList);
//        return exerciseList;
//    }

    @GetMapping
    public String exerciseAll(Model model){
        List<Exercise> exercises = exerciseRepository.findAll();
        LOGGER.info("//////////////"+exercises.get(0));
        model.addAttribute("nav_selected","nav_exercises");
        model.addAttribute("exercises", exercises);
        return "exercises";
    }
}
