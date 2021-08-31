package ru.abtank.controllers;

import ru.abtank.model.Box;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TempController {
//    // GET http://localhost:8189/app/temp
//    @RequestMapping(value = "/temp", method = RequestMethod.GET)
//    public String showTempPage() {
//        return "temp-page";
//    }
//
//    // GET http://localhost:8189/app/simple_str
//    @RequestMapping(value = "/simple_str", method = RequestMethod.GET)
//    @ResponseBody
//    public String showSimpleString() {
//        return "Hello, World!!!";
//    }
//
//    // GET http://localhost:8189/app/show_obj
//    // @RequestMapping(value = "/show_obj", method = RequestMethod.GET)
//    @GetMapping("/show_obj")
//    @ResponseBody
//    public Box showJavaObject() {
//        return new Box(1L, "Green", 10);
//    }
//
//    // GET http://localhost:8189/app/show_obj
//    @PostMapping("/show_obj")
//    @ResponseBody
//    public Box showJavaObjectPost() {
//        return new Box(1L, "Red", 10);
//    }
//
//    // POST http://localhost:8189/app/simple_post
//    // @RequestMapping(value = "/simple_post", method = RequestMethod.POST)
//    @PostMapping("/simple_post")
//    @ResponseBody
//    public String simplePostRequest() {
//        return "Post request";
//    }
//
//    // GET http://localhost:8189/app/hello?name=Bob
//    @GetMapping("/hello")
//    @ResponseBody
//    public String sayHello(@RequestParam(required = false) String name, @RequestParam(required = false) String surname) {
//        if (name == null && surname == null) {
//            return "Hello, stranger!!!";
//        }
//        StringBuilder resultName = new StringBuilder();
//        if (name != null) {
//            resultName.append(name).append(" ");
//        }
//        if (surname != null) {
//            resultName.append(surname);
//        } else {
//            resultName.setLength(resultName.length() - 1);
//        }
//        return String.format("Hello, %s!!!", resultName.toString());
//    }
//
//    @GetMapping("/show_product/{id}/show")
//    @ResponseBody
//    public String showProductById(@PathVariable Long id) {
//        return "Product #" + id;
//    }
}
