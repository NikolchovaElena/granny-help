package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.service.UserServiceModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    @GetMapping(GlobalConstants.URL_INDEX)
    public ModelAndView index() {
        return view("index");
    }

    @GetMapping(GlobalConstants.URL_USER_HOME)
    public ModelAndView home(Principal principal, ModelAndView modelAndView) {

        if (principal == null) {
            return view("index");
        }
        return view("home", modelAndView);
    }

    @GetMapping(GlobalConstants.URL_ABOUT)
    public ModelAndView about() {
        return view("about");
    }

    @GetMapping("/error")
    @PreAuthorize("isAnonymous()")
    public ModelAndView error() {
        return view("error");
    }

    @GetMapping("/account")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public ModelAndView account() {
        return view("account");
    }

    @GetMapping("/users/show")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView users(ModelAndView modelAndView) {
        List<UsersShowViewModel> model = this.userService.findAllUsers()
                .stream()
                .map(m -> this.modelMapper.map(m, UsersShowViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("users", modelAndView);
    }

}
