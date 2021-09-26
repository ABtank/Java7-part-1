package ru.abtank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.persist.entities.Mode;
import ru.abtank.persist.repositories.ModeSpecification;
import ru.abtank.servises.ModeService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/mode") // localhost:8080/fitness/mode
public class ModeController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ModeController.class);
    private ModeService modeService;

    @Autowired
    public void setModeService(ModeService modeService) {
        this.modeService = modeService;
    }

    //весь список юзеров
    @GetMapping
    public String allModes(Model model) {
        model.addAttribute("modes", modeService.findAll());
        model.addAttribute("mode", new Mode());
        model.addAttribute("time", getDate());
        model.addAttribute("nav_selected", "nav_modes");
        LOGGER.info("GET ALL modeS: ");
        return "modes";
    }


    @GetMapping("/{id}")
    public String editMode(@PathVariable("id") Integer id, Model model) throws SQLException {
        Mode mode = modeService.findById(id).orElseThrow(()->new NotFoundException(Mode.class.getSimpleName(), id," not Found!"));
        LOGGER.info("EDIT mode: " + mode.getName());
        model.addAttribute("mode", mode);
        model.addAttribute("nav_selected", "nav_modes");
        return "mode";
    }

    @PostMapping("/update")
    @Secured("ROLE_ADMIN")
    public String updateMode(@ModelAttribute("mode") @Valid Mode mode, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        LOGGER.info("START UPDATE OR INSERT MODE: " + mode.getName());
        if (bindingResult.hasErrors()) {
            return "mode";
        }
        Specification<Mode> spec = ModeSpecification.trueLiteral();
        spec = spec.and(ModeSpecification.findByName(mode.getName()));
        if(mode.getId() != null){
            spec = spec.and(ModeSpecification.idNotEqual(mode.getId()));
        }
        List<Mode> chekEquals = modeService.findAll(spec);
        LOGGER.info("!chekEquals.isEmpty() {}", !chekEquals.isEmpty());
        if (!chekEquals.isEmpty()) {
            bindingResult.rejectValue("name", "error.name", "такая mode уже есть");
            model.addAttribute("mode", mode);
            return "mode";
        }
        String msg = (mode.getId() != null) ? "Susses update mode " : "Susses create mode ";
        modeService.save(mode);
        msg += mode.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/mode";
    }

    @GetMapping("/create")
    public String createMode(Model model) {
        LOGGER.info("CREATE new mode");
        model.addAttribute("mode", new Mode());
        model.addAttribute("nav_selected", "ADD_NEW");
        return "mode";
    }

    @DeleteMapping("{id}/delete")
    public String deleteMode(@PathVariable("id") Integer id) {
        LOGGER.info("DELETE mode id=" + id);
        modeService.deleteById(id);
        return "redirect:/mode";
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}