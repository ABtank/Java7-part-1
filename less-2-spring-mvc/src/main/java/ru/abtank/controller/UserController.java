package ru.abtank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.abtank.persistance.User;
import ru.abtank.persistance.UserRepository;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/user") // localhost:8080/mvc/user
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //весь список юзеров
    @GetMapping
    public String allUsers(Model model) throws SQLException {
        List<User> allUser = userRepository.getAllUsers();
        model.addAttribute("users", allUser);
    return "users"; // возврат названия html файла из view (представлений)
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) throws SQLException {
            User user = userRepository.findById(id);
            model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(User user) throws SQLException {
            userRepository.update(user);
        return "redirect:/user";
    }


}
