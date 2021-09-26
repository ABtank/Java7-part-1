package ru.abtank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.dto.UserDto;
import ru.abtank.persist.entities.Role;
import ru.abtank.persist.entities.User;
import ru.abtank.persist.repositories.UserSpecification;
import ru.abtank.servises.RoleService;
import ru.abtank.servises.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user") // localhost:8080/fitness/user
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    //весь список юзеров
    @GetMapping
    public String allUsers(Model model,
                           @RequestParam Map <String,String> params,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sort") Optional<String> sort,
                           @RequestParam("direction") Optional<String> direction,
                           Principal principal
    ) {

        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(10), direction.isEmpty() ? Sort.Direction.ASC : Sort.Direction.DESC, sort.orElse("id"));
        Page<UserDto> userPage = userService.findAll(params, pageRequest);
        model.addAttribute("usersPage", userPage);

        List<UserDto> userList = userService.findAll();
        model.addAttribute("userList", userList);
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", new User());
        model.addAttribute("roles", roles);

        model.addAttribute("time", getDate());
        model.addAttribute("nav_selected", "nav_users");
        LOGGER.info("GET ALL USERS: " + userPage.stream()
                .map(UserDto::getLogin)
                .collect(Collectors.joining(", ")));
        return "users";
    }

    @GetMapping("/json")
    @ResponseBody // возвращает json
    public List <UserDto> userAll(){
        System.out.println("/////////ResponseBody//////////");
        UserDto user = userService.findById(1).map(UserDto:: new).get();
        List<UserDto> userDtos = userService.findAll();
//            List<userDto> userDtos2 = userRepository.findAlluser();
//            System.out.println("/////////userDto//////////"+userDtos2);
        return userDtos;
    }


    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) throws SQLException {
        User user = userService.findById(id).orElseThrow(() -> new NotFoundException(User.class.getSimpleName(), id, "not Found!"));
        LOGGER.info("EDIT USER: " + user.toString());
        LOGGER.info("CREATOR USER: " + user.getCreator().getLogin());
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        model.addAttribute("nav_selected", "nav_users");
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        String msg;
        LOGGER.info(principal.getName() + " START UPDATE OR INSERT USER: " + user.toString());
        User creator = userService.findByLogin(principal.getName()).orElseThrow(() -> new NotFoundException("creator not Found!"));
        LOGGER.info(creator.getLogin() + " " + creator.getId() + " START UPDATE OR INSERT USER: " + user.toString());
        if (bindingResult.hasErrors()) {
            return (user.getId() != null) ? "redirect:/user/" + user.getId() : "users";
        }
        Specification<User> spec = UserSpecification.trueLiteral();
        spec = spec.and(UserSpecification.findBylogin(user.getLogin()));
        spec = spec.or(UserSpecification.findByEmail(user.getEmail()));
        if(user.getId() != null){
            spec = spec.and(UserSpecification.idNotEqual(user.getId()));
        }
        List<User> chekEquals = userService.findAll(spec);
        LOGGER.info("!chekEquals.isEmpty() {}", !chekEquals.isEmpty());
        if (!chekEquals.isEmpty()) {
            msg = "Login or email already exists";
            redirectAttributes.addFlashAttribute("exception", msg);
            return (user.getId() != null) ? "redirect:/user/" + user.getId() : "redirect:/user";
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            bindingResult.rejectValue("matchingPassword", "error.matchingPassword", "пароль не совпал");
            return (user.getId() != null) ? "redirect:/user/" + user.getId() : "redirect:/user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreator(creator);
        msg = (user.getId() != null) ? creator.getLogin() + " " + creator.getId() + " Success update User " : creator.getLogin() + " " + creator.getId() + " Success create User ";
        userService.save(user);
        LOGGER.info("SAVE SAVE SAVE");
        msg += user.getLogin();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/user";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        LOGGER.info("CREATE USER");
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("nav_selected", "ADD_NEW");
        return "user";
    }

    @DeleteMapping("{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("DELETE USER id=" + id);
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "Success DELETE User");
        return "redirect:/user";
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}