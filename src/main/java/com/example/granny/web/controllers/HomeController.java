package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.domain.models.view.CauseViewModel;
import com.example.granny.domain.models.view.UserViewModel;
import com.example.granny.service.api.CauseService;
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
    private final CauseService causeService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(UserService userService, CauseService causeService, ModelMapper modelMapper) {
        this.userService = userService;
        this.causeService = causeService;
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
        UserServiceModel user = userService.findUserByEmail(principal.getName());

        List<CauseServiceModel> approvedServiceModel = causeService.findAllApproved(user.getId());
        List<CauseServiceModel> pendingServiceModel = causeService.findAllPending(user.getId());
        List<CauseServiceModel> pinnedServiceModel = userService.findAllPinned(user.getId());

        modelAndView.addObject("approved", fetchCauses(approvedServiceModel));
        modelAndView.addObject("pending", fetchCauses(pendingServiceModel));
        modelAndView.addObject("pinned", fetchCauses(pinnedServiceModel));
        return view("home", modelAndView);
    }

    @GetMapping(GlobalConstants.URL_ABOUT)
    public ModelAndView about(ModelAndView modelAndView) {
        List<UserViewModel> model = userService.findFourRandomUsers()
                .stream()
                .map(u -> modelMapper.map(u, UserViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("about", modelAndView);
    }

    @GetMapping("/error")
    @PreAuthorize("isAnonymous()")
    public ModelAndView error() {
        return view("error");
    }

    private List<CauseViewModel> fetchCauses(List<CauseServiceModel> models) {
        return models.stream()
                .map(c -> modelMapper.map(c, CauseViewModel.class))
                .collect(Collectors.toList());
    }
}
