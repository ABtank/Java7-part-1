package ru.abtank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.dto.ExerciseDto;
import ru.abtank.entities.Category;
import ru.abtank.entities.Character;
import ru.abtank.entities.Exercise;
import ru.abtank.entities.Role;
import ru.abtank.entities.User;
import ru.abtank.repositories.*;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {
    private ExerciseRepository exerciseRepository;
    private CategoryRepository categoryRepository;
    private CharacterRepository characterRepository;
    private UserRepository userRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public void setExerciseRepository(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Autowired
    public void setCharacterRepository(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        List<Category> categories = categoryRepository.findAll();
        List<Character> characters = characterRepository.findAll();
        Exercise exercise = new Exercise();
        LOGGER.info("//////////////"+exercises.get(0));
        model.addAttribute("nav_selected","nav_exercises");
        model.addAttribute("exercise", exercise);
        model.addAttribute("exercises", exercises);
        model.addAttribute("categories", categories);
        model.addAttribute("characters", characters);
        return "exercises";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(()->new NotFoundException(Exercise.class.getSimpleName(), id,"not Found!"));
        List<Category> categories = categoryRepository.findAll();
        List<Character> characters = characterRepository.findAll();
        LOGGER.info("EDIT Exercise: " + exercise.toString());
        LOGGER.info("CREATOR Exercise: " + exercise.getCreator().getLogin());
        model.addAttribute("exercise", exercise);
        model.addAttribute("categories", categories);
        model.addAttribute("characters", characters);
        model.addAttribute("nav_selected", "nav_exercises");
        return "exercise";
    }

    @DeleteMapping("{id}/delete")
    public String deleteExercise(@PathVariable("id") Integer id) {
        LOGGER.info("@DeleteMapping id{}",id);
        exerciseRepository.deleteById(id);
        LOGGER.info("Exercise id={} deleted",id);
        return "redirect:/exercise";
    }

    @PostMapping("/update")
    public String updateExercise(@ModelAttribute("exercise") Exercise exercise, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        String msg;
        LOGGER.info(principal.getName()+" START UPDATE OR INSERT EXERCISE: " + exercise.toString());
        LOGGER.info("CLASS EXERCISE: " + exercise.getName().getClass());
        User creator = userRepository.findByLogin(principal.getName()).orElseThrow(()->new NotFoundException("creator not Found!"));
        LOGGER.info(creator.getLogin()+" "+creator.getId()+" START UPDATE OR INSERT EXERCISE: " + exercise.toString());
        if (bindingResult.hasErrors()) {
            return (exercise.getId() != null)?"exercise":"exercises";
        }
        Specification<Exercise> spec = ExerciseSpecification.trueLiteral();
        spec = spec.and(ExerciseSpecification.findByName(exercise.getName()));
        List<Exercise> chekEquals = exerciseRepository.findAll(spec);
        if(!chekEquals.isEmpty()){
            msg = "Exercise already exists";
            redirectAttributes.addFlashAttribute("exception", msg);
            return (exercise.getId() != null)?"exercise":"redirect:/exercise";
        }
        exercise.setCreator(creator);
        msg = (exercise.getId() != null) ? creator.getLogin()+" "+creator.getId()+" Susses update Exercise " : creator.getLogin()+" "+creator.getId()+" Susses create exercise ";
        exerciseRepository.save(exercise);
        msg += exercise.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/exercise";
    }
}
