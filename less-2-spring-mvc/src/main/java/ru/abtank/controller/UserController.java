package ru.abtank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.abtank.persistance.User;
import ru.abtank.persistance.UserRepository;

import java.sql.SQLException;
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
        List<User> allUser = userRepository.getAllUsers();
        model.addAttribute("users", allUser);
        LOGGER.info("GET ALL USERS: "+allUser.stream()
                .map(User::getLogin)
                .collect(Collectors.joining(", ")));
    return "users"; // возврат названия html файла из view (представлений)
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) throws SQLException {
            User user = userRepository.findById(id);
        LOGGER.info("EDIT USER: "+user.toString());
            model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(User user) throws SQLException {
        if(user.getId() > 0) {
            LOGGER.info("UPDATE USER: "+user.toString());
            userRepository.update(user);
        } else{
            LOGGER.info("INSERT USER: "+user.toString());
            userRepository.insert(user);
        }
        return "redirect:/user";
    }

    @GetMapping("/create")
    public  String createUser (Model model){
        User user = new User(0,"","","");
        LOGGER.info("CREATE USER: "+user.toString());
        model.addAttribute("user", user);
        return "user";
    }

    @DeleteMapping ("{id}/delete")
    public String deleteUser(@PathVariable("id") Long id){
        LOGGER.info("DELETE USER id="+id +" "+ userRepository.deleteById(id));
        return "redirect:/user";
    }

}
