package com.example.granny.web.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Throwable e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors");
        modelAndView.addObject("error", e.getMessage());

        return modelAndView;
    }
}
