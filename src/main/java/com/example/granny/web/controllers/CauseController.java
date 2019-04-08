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

@Controller
public class CauseController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CauseController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/cause/details")
    @PreAuthorize(GlobalConstants.IS_ANONYMOUS)
    ModelAndView causeDetails(Principal principal,
                              ModelAndView modelAndView) {
        UserServiceModel userServiceModel = userService.findUserByEmail(principal.getName());
        UserViewModel model = this.modelMapper.map(userServiceModel, UserViewModel.class);
        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("cause-details", modelAndView);
    }

}
