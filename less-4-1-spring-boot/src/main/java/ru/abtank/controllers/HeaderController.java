package ru.abtank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.abtank.persist.entities.User;
import ru.abtank.persist.repositories.ExerciseRepository;
import ru.abtank.persist.repositories.UserRepository;

import java.security.Principal;

@ControllerAdvice
public class HeaderController {

    private UserRepository userRepository;
    private ExerciseRepository exerciseRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setExerciseRepository(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }


    @ModelAttribute
    public void nav(Principal principal, Model model ) {
        if (principal != null) {
            User usr = userRepository.findByLogin(principal.getName()).orElse(new User());
            model.addAttribute("usr_id", usr.getId());
            model.addAttribute("exercises_count",exerciseRepository.count());
            model.addAttribute("users_count",userRepository.count());
        }

    }
}
