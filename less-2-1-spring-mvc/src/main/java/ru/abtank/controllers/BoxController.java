package ru.abtank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.abtank.model.Box;
import ru.abtank.services.BoxService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/boxes")
public class BoxController {
    private BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping("/all")
    public String getAllBoxes(Model model) {
        model.addAttribute("frontBoxes", boxService.getAllBoxes());
        return "all_boxes";
    }

    @PostMapping("/add")
//    public String addNewBox(@RequestParam Long id, @RequestParam String color, @RequestParam int size) {
    public String addNewBox(@ModelAttribute Box box) {
        boxService.save(box);
        return "redirect:/boxes/all";
    }

    @PostMapping("/json/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewJsonBox(@RequestBody Box box) {
        boxService.save(box);
    }


}
