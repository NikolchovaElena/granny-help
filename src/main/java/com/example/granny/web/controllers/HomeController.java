package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.domain.models.view.UserViewModel;
import com.example.granny.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

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
    public ModelAndView about(ModelAndView modelAndView) {
        List<UserViewModel> model = userService.findFourRandomUsers()
                .stream()
                .map(u->modelMapper.map(u,UserViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("about", modelAndView);
    }

    @GetMapping("/error")
    @PreAuthorize("isAnonymous()")
    public ModelAndView error() {
        return view("error");
    }

//    @GetMapping("/account")
//    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
//    public ModelAndView account() {
//        return view("account");
//    }


}
