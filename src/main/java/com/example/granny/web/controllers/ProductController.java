package com.example.granny.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController extends BaseController {


    @GetMapping("/shop")
    public ModelAndView allProducts() {
        return view("shop");
    }




}
