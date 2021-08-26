package ru.abtank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    public String welcomePage(Model model){
        model.addAttribute("msg", "You loggined!");
        return "welcome";
    }
}
