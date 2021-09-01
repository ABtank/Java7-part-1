package ru.abtank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.abtank.model.Student;
import ru.abtank.services.StudentsService;

import java.util.List;

// root: http://localhost:8189/app/students

@Controller
@RequestMapping("/students")
public class StudentsController {
    private StudentsService studentsService;

    @Autowired
    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping
    public String showAllStudents(Model model) {
        List<Student> students = studentsService.getAllStudents();
        model.addAttribute("students", students);
        return "all_students";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add_student_form";
    }

    // public String saveNewStudent(@RequestParam Long id, @RequestParam String name, @RequestParam int score) {
    @PostMapping("/add")
    public String saveNewStudent(@ModelAttribute Student newStudent) {
        studentsService.saveOrUpdateStudent(newStudent);
        return "redirect:/students/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentsService.findById(id));
        return "edit_student_form";
    }

    @PostMapping("/edit")
    public String modifyStudent(@ModelAttribute Student modifiedStudent) {
        studentsService.saveOrUpdateStudent(modifiedStudent);
        return "redirect:/students/";
    }

    @GetMapping("/json/{id}")
    @ResponseBody
    public Student showJsonStundent(@PathVariable Long id) {
        return studentsService.findById(id);
    }
}
