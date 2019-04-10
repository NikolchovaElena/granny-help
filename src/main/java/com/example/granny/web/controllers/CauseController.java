package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.binding.CauseSubmitBindingModel;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.domain.models.view.CauseViewModel;
import com.example.granny.domain.models.view.LocationViewModel;
import com.example.granny.service.api.CauseService;
import com.example.granny.service.api.LocationService;
import com.example.granny.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CauseController extends BaseController {

    private final UserService userService;
    private final CauseService causeService;
    private final LocationService locationService;
    private final ModelMapper modelMapper;

    @Autowired
    public CauseController(UserService userService,
                           CauseService causeService,
                           LocationService locationService,
                           ModelMapper modelMapper) {
        this.userService = userService;
        this.causeService = causeService;
        this.locationService = locationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/cause/submit")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    ModelAndView submitCause(ModelAndView modelAndView) {
        modelAndView.addObject(GlobalConstants.MODEL, new CauseSubmitBindingModel());
        addLocations(modelAndView);
        return view("submit-cause", modelAndView);
    }

    @PostMapping("/cause/submit")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    ModelAndView submitCauseConfirm(@Valid @ModelAttribute(name = GlobalConstants.MODEL)
                                            CauseSubmitBindingModel model,
                                    BindingResult bindingResult,
                                    Principal principal,
                                    ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.MODEL, model);
            addLocations(modelAndView);
            return view("submit-cause", modelAndView);
        }
        CauseServiceModel causeServiceModel = modelMapper.map(model, CauseServiceModel.class);
        UserServiceModel author = userService.findUserByEmail(principal.getName());
        causeServiceModel.setAuthor(author);

        causeService.submit(causeServiceModel);
        return redirect("home");
    }

    @GetMapping("/cause/edit/{id}")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    ModelAndView editCause(@PathVariable("id") Integer id,
                           ModelAndView modelAndView) {
        CauseServiceModel model = causeService.findById(id);
        modelAndView.addObject(GlobalConstants.MODEL, model);
        addLocations(modelAndView);
        return view("edit-cause", modelAndView);
    }

    @PostMapping("/cause/edit/{id}")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    ModelAndView editCauseConfirm(@Valid @ModelAttribute(name = GlobalConstants.MODEL)
                                            CauseSubmitBindingModel model,
                                    BindingResult bindingResult,
                                    Principal principal,
                                    ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.MODEL, model);
            addLocations(modelAndView);
            return view("submit-cause", modelAndView);
        }
        CauseServiceModel causeServiceModel = modelMapper.map(model, CauseServiceModel.class);
        UserServiceModel author = userService.findUserByEmail(principal.getName());
        causeServiceModel.setAuthor(author);

        causeService.submit(causeServiceModel);
        return redirect("home");
    }


    @GetMapping("/cause/details/{id}")
    ModelAndView causeDetails(@PathVariable("id") Integer id,
                              ModelAndView modelAndView) {
        CauseServiceModel causeServiceModel = causeService.findById(id);
        CauseViewModel model = modelMapper.map(causeServiceModel, CauseViewModel.class);
        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("cause-details", modelAndView);
    }

    @GetMapping("/causes")
    ModelAndView causesAll(ModelAndView modelAndView) {
        List<CauseServiceModel> causes = causeService.findAll();

        List<CauseViewModel> model = causes.stream()
                .map(c -> modelMapper.map(c, CauseViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("causes", modelAndView);
    }


    private void addLocations(ModelAndView modelAndView) {
        modelAndView.addObject("locations", this.locationService
                .findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, LocationViewModel.class))
                .collect(Collectors.toList()));
    }


}
