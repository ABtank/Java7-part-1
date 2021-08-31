package ru.abtank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.abtank.model.Box;
import ru.abtank.services.BoxService;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class HelloController {
    // context-path: http://localhost:8189/app
    // http://localhost:8189/app/hello
    private BoxService boxService;

    @Autowired
    public void setBoxService(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping("/box_page")
    public String showBoxPage(Model model) {
        List<Box> list = boxService.getAllBoxes();
        model.addAttribute("boxes", list);
        return "hello";
    }

    // http://localhost:8189/app/hello_user?name=Bob&surname=Johnson
    @GetMapping("/hello_user")
    @ResponseBody
    public String sayHello(@RequestParam(name = "name", defaultValue = "Unknown") String username, @RequestParam(defaultValue = "Unknown") String surname) {
        return String.format("Hello, %s %s!!!", username, surname);
    }

    // http://localhost:8189/app/docs/100/info
    @GetMapping("/docs/{doc_id}/info")
    @ResponseBody
    public String showDocInfo(@PathVariable(name = "doc_id") Long id) {
        return "Document #" + id;
    }

    @PostMapping("/add_box")
    public String addNewBox(@RequestParam String color, @RequestParam int size) {
        Box box = new Box(1L, color, size);
        boxService.save(box);
        return "redirect:/box_page";
    }

    @GetMapping("/delete_box/{id}")
    public String deleteBox(@PathVariable Long id) {
        boxService.deleteById(id);
        return "redirect:/box_page";
    }
}
