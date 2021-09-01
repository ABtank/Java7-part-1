package ru.abtank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

//    // root (context path) = http://localhost:8189/app
//    // GET http://localhost:8189/app/hello?name=Alex
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
//    @ResponseBody
//    public String sayHello(@RequestParam(required = false) String name) {
//        if (name == null) {
//            return "Hello, stranger!!!";
//        }
//        return String.format("Hello, %s!!!", name);
//    }
//
//    // GET http://localhost:8189/app/add?n1=100&n2=1000
//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    @ResponseBody
//    public Integer addTwoNumbers(@RequestParam(name = "n1") Integer value1, @RequestParam(name = "n2") Integer value2) {
//        return value1 + value2;
//    }
//
//    // GET http://localhost:8189/app/users/info/{id = 2}/confirm
//    @GetMapping("/users/info/{id}/confirm")
//    @ResponseBody
//    public String getUserInfo(@PathVariable Long id) {
//        return "User Info #" + id;
//    }
//
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }
//
//    @GetMapping("/check/java/data")
//    public String checkJavaData(@RequestParam String name, Model model) {
//        model.addAttribute("name", name);
//        return "check_data";
//    }
}
