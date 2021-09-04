package ru.abtank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice // общий для всех . -> ловит все ошибки из всех контроллеров
public class ExceptionHandlingController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundException (NotFoundException exception){
        ModelAndView modelAndView = new ModelAndView("404");
        modelAndView.getModel().put("exception", exception.getMessage());
        modelAndView.getModel().put("class", exception.getMessage());
        return modelAndView;
    }
}
