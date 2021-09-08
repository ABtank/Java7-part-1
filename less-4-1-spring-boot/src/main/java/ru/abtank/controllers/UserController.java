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
import ru.abtank.persist.entities.Role;
import ru.abtank.persist.entities.User;
import ru.abtank.persist.repositories.RoleRepository;
import ru.abtank.persist.repositories.UserRepository;
import ru.abtank.persist.repositories.UserSpecification;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user") // localhost:8080/fitness/user
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    //весь список юзеров
    @GetMapping
    public String allUsers(Model model,
                           @RequestParam(value = "check_login_filter", required = false) Boolean check_login_filter,
                           @RequestParam(value = "check_email_filter", required = false) Boolean check_email_filter,
                           @RequestParam(value = "login_filter", required = false) String login_filter,
                           @RequestParam(value = "email_filter", required = false) String email_filter,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sort") Optional<String> sort,
                           @RequestParam("direction") Optional<String> direction,
                           Principal principal
    ) {
        LOGGER.info("check_login_filter {} \n check_email_filter {} \n", check_login_filter, check_email_filter);

        Specification<User> spec = UserSpecification.trueLiteral();
        if (check_login_filter != null && !login_filter.isEmpty()) {
            spec = spec.and(UserSpecification.loginContains(login_filter));
        }
        if (check_email_filter != null && !email_filter.isEmpty()) {
            spec = spec.and(UserSpecification.emailContains(email_filter));
        }

//        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(10), direction.isEmpty() ? Sort.Direction.ASC : Sort.Direction.DESC, sort.orElse("id"));
//        Page<User> userPage = userRepository.findAll(spec, pageRequest);
//        model.addAttribute("usersPage", userPage);

        List<User> userPage = userRepository.findAll(spec);
        model.addAttribute("usersPage", userPage);
        User user = new User();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        model.addAttribute("time", getDate());
        model.addAttribute("nav_selected", "nav_users");
        LOGGER.info("GET ALL USERS: " + userPage.stream()
                .map(User::getLogin)
                .collect(Collectors.joining(", ")));
        return "users";
    }


    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) throws SQLException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class.getSimpleName(), id, "not Found!"));
        LOGGER.info("EDIT USER: " + user.toString());
        LOGGER.info("CREATOR USER: " + user.getCreator().getLogin());
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        model.addAttribute("nav_selected", "nav_users");
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        String msg;
        LOGGER.info(principal.getName() + " START UPDATE OR INSERT USER: " + user.toString());
        User creator = userRepository.findByLogin(principal.getName()).orElseThrow(() -> new NotFoundException("creator not Found!"));
        LOGGER.info(creator.getLogin() + " " + creator.getId() + " START UPDATE OR INSERT USER: " + user.toString());
        if (bindingResult.hasErrors()) {
            return (user.getId() != null) ? "redirect:/user/" + user.getId() : "users";
        }
        Specification<User> spec = UserSpecification.trueLiteral();
        spec = spec.and(UserSpecification.findBylogin(user.getLogin()));
        spec = spec.or(UserSpecification.findByEmail(user.getEmail()));
        List<Integer> chekEquals = userRepository.findAll(spec).stream().map(User::getId).collect(Collectors.toList());
        chekEquals.remove(user.getId());
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
        msg = (user.getId() != null) ? creator.getLogin() + " " + creator.getId() + " Susses update User " : creator.getLogin() + " " + creator.getId() + " Susses create User ";
        userRepository.save(user);
        LOGGER.info("SAVE SAVE SAVE");
        msg += user.getLogin();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/user";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        User user = new User();
        List<Role> roles = roleRepository.findAll();
        LOGGER.info("ROLES: " + roles);
        LOGGER.info("CREATE USER: " + user.toString());
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("nav_selected", "ADD_NEW");
        return "user";
    }

    @DeleteMapping("{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        LOGGER.info("DELETE USER id=" + id);
        userRepository.deleteById(id);
        return "redirect:/user";
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}