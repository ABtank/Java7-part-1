package ru.abtank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.abtank.persist.entities.User;
import ru.abtank.persist.repositories.ExerciseRepository;
import ru.abtank.persist.repositories.ModeRepository;
import ru.abtank.persist.repositories.RoleRepository;
import ru.abtank.persist.repositories.UserRepository;

import java.security.Principal;

@ControllerAdvice
public class HeaderController {

    private UserRepository userRepository;
    private ExerciseRepository exerciseRepository;
    private RoleRepository roleRepository;
    private ModeRepository modeRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setExerciseRepository(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }
    @Autowired
    public void setModeRepository(ModeRepository modeRepository) {
        this.modeRepository = modeRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @ModelAttribute
    public void nav(Principal principal, Model model ) {
        if (principal != null) {
            User usr = userRepository.findByLogin(principal.getName()).orElse(new User());
            model.addAttribute("usr_id", usr.getId());
            model.addAttribute("exercises_count", exerciseRepository.count());
            model.addAttribute("users_count", userRepository.count());
            model.addAttribute("roles_count", roleRepository.count());
            model.addAttribute("modes_count", modeRepository.count());
        }

    }
}
