package ru.abtank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.dto.RoleDto;
import ru.abtank.servises.RoleService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@RequestMapping("/role") // localhost:8080/fitness/user
public class RoleController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RoleController.class);


    private RoleService roleService;
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    //весь список юзеров
    @GetMapping
    public String allRoles(Model model) {

        model.addAttribute("roles", roleService.findAllDto());
        model.addAttribute("role", new RoleDto());

        model.addAttribute("time", getDate());
        model.addAttribute("nav_selected", "nav_roles");
        LOGGER.info("GET ALL roleS: ");
        return "roles";
    }


    @GetMapping("/{id}")
    public String editRole(@PathVariable("id") Integer id, Model model) throws SQLException {
        RoleDto roleDto = roleService.findByIdDto(id).orElseThrow(()->new NotFoundException("Role", id," not Found!"));
        LOGGER.info("EDIT ROLE: " + roleDto.toString());
        model.addAttribute("role", roleDto);
        model.addAttribute("nav_selected", "nav_roles");
        return "role";
    }

    @PostMapping("/update")
    @Secured("ROLE_ADMIN")
    public String updateRole(@ModelAttribute("role") @Valid RoleDto roleDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        LOGGER.info("START UPDATE OR INSERT ROLE: " + roleDto.toString());
        if (bindingResult.hasErrors()) {
            model.addAttribute("role", roleDto);
            return "role";
        }
        if (!roleService.findByName(roleDto.getName()).isEmpty()) {
            bindingResult.rejectValue("name", "error.name", "такая роль уже есть");
            model.addAttribute("role", roleDto);
            return "role";
        }
        String msg = (roleDto.getId() != null) ? "Susses update role " : "Susses create role ";
        roleService.saveOrUpdate(roleDto);
        msg += roleDto.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/role";
    }

    @GetMapping("/create")
    public String createRole(Model model) {
        LOGGER.info("CREATE ROLE: ");
        model.addAttribute("role", new RoleDto());
        model.addAttribute("nav_selected", "ADD_NEW");
        return "role";
    }

    @DeleteMapping("{id}/delete")
    public String deleteRole(@PathVariable("id") Integer id) {
        LOGGER.info("DELETE ROLE id=" + id);
        roleService.deleteById(id);
        return "redirect:/role";
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}