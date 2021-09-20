package ru.abtank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.persist.entities.Role;
import ru.abtank.persist.repositories.RoleRepository;

import javax.validation.Valid;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/role") // localhost:8080/fitness/user
public class RoleController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleRepository roleRepository;

    //весь список юзеров
    @GetMapping
    public String allRoles(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sort") Optional<String> sort,
                           @RequestParam("direction") Optional<String> direction
    ) {


//        c пагинацией
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), direction.isEmpty() ? Sort.Direction.ASC : Sort.Direction.DESC, sort.orElse("id"));

        Page<Role> rolePage = roleRepository.findAll(pageRequest);
        model.addAttribute("rolesPage", rolePage);

        model.addAttribute("time", getDate());
        model.addAttribute("nav_selected", "nav_roles");
        LOGGER.info("GET ALL roleS: " + rolePage.stream()
                .map(Role::getName)
                .collect(Collectors.joining(", ")));
        return "roles";
    }


    @GetMapping("/{id}")
    public String editRole(@PathVariable("id") Integer id, Model model) throws SQLException {
        Role role = roleRepository.findById(id).orElseThrow(()->new NotFoundException(Role.class.getSimpleName(), id," not Found!"));
        LOGGER.info("EDIT ROLE: " + role.toString());
        model.addAttribute("role", role);
        model.addAttribute("nav_selected", "nav_roles");
        return "role";
    }

    @PostMapping("/update")
    @Secured("ADMIN")
    public String updateRole(@ModelAttribute("role") @Valid Role role, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        LOGGER.info("START UPDATE OR INSERT ROLE: " + role.toString());
        if (bindingResult.hasErrors()) {
            return "role";
        }
        if (!roleRepository.findByName(role.getName()).isEmpty()) {
            bindingResult.rejectValue("name", "error.name", "такая роль уже есть");
            return "role";
        }
        String msg = (role.getId() != null) ? "Susses update role " : "Susses create role ";
        roleRepository.save(role);
        msg += role.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/role";
    }

    @GetMapping("/create")
    public String createRole(Model model) {
        Role role = new Role();
        LOGGER.info("CREATE ROLE: " + role.toString());
        model.addAttribute("role", role);
        model.addAttribute("nav_selected", "ADD_NEW");
        return "role";
    }

    @DeleteMapping("{id}/delete")
    public String deleteRole(@PathVariable("id") Integer id) {
        LOGGER.info("DELETE ROLE id=" + id);
        roleRepository.deleteById(id);
        return "redirect:/role";
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}