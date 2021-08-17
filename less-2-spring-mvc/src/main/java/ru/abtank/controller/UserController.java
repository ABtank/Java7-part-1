package ru.abtank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.abtank.persist.entity.User;
import ru.abtank.persist.repo.UserRepository;

import javax.validation.Valid;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user") // localhost:8080/mvc/user
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    //весь список юзеров
    @GetMapping
    public String allUsers(Model model) throws SQLException {
        List<User> allUser = userRepository.findAll();
        model.addAttribute("users", allUser);
        model.addAttribute("time", getDate());
        model.addAttribute("nav_selected", "nav_users");
        LOGGER.info("GET ALL USERS: " + allUser.stream()
                .map(User::getLogin)
                .collect(Collectors.joining(", ")));
        return "users"; // возврат названия html файла из view (представлений)
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) throws SQLException {
        User user = userRepository.findById(id).get();
        LOGGER.info("EDIT USER: " + user.toString());
        model.addAttribute("user", user);
        model.addAttribute("nav_selected", "nav_users");
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) throws SQLException {
        LOGGER.info("START UPDATE OR INSERT USER: " + user.toString());
        if (bindingResult.hasErrors()) {
            return "user";
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            bindingResult.rejectValue("password", "error.password", "пароль не совпал");
            return "user";
        }
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        User user = new User();
        LOGGER.info("CREATE USER: " + user.toString());
        model.addAttribute("user", user);
        model.addAttribute("nav_selected", "ADD_NEW");
        return "user";
    }

    @DeleteMapping("{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        LOGGER.info("DELETE USER id=" + id );
        userRepository.deleteById(id);
        return "redirect:/user";
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }


}
