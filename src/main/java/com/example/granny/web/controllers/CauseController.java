package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.binding.CauseFormBindingModel;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.domain.models.view.CauseViewModel;
import com.example.granny.domain.models.view.CommentViewModel;
import com.example.granny.domain.models.view.LocationViewModel;
import com.example.granny.error.CauseNotFoundException;
import com.example.granny.service.api.CauseService;
import com.example.granny.service.api.CommentService;
import com.example.granny.service.api.LocationService;
import com.example.granny.service.api.UserService;
import com.example.granny.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CauseController extends BaseController {

    private final UserService userService;
    private final CauseService causeService;
    private final CommentService commentService;
    private final LocationService locationService;
    private final ModelMapper modelMapper;

    @Autowired
    public CauseController(UserService userService,
                           CauseService causeService,
                           CommentService commentService, LocationService locationService,
                           ModelMapper modelMapper) {
        this.userService = userService;
        this.causeService = causeService;
        this.commentService = commentService;
        this.locationService = locationService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("add cause")
    @GetMapping("/causes/form")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    ModelAndView submitCause(ModelAndView modelAndView) {
        modelAndView.addObject(GlobalConstants.MODEL, new CauseFormBindingModel());
        addLocations(modelAndView);
        return view("causes-submit", modelAndView);
    }

    @PostMapping("/causes/form")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    ModelAndView submitCauseConfirm(@Valid @ModelAttribute(name = GlobalConstants.MODEL)
                                            CauseFormBindingModel model,
                                    BindingResult bindingResult,
                                    Principal principal,
                                    ModelAndView modelAndView) throws IOException {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.MODEL, model);
            addLocations(modelAndView);
            return view("causes-submit", modelAndView);
        }
        causeService.submit(model, principal.getName());
        return redirect("/home");
    }

    @PageTitle("edit cause")
    @GetMapping("/causes/form/{id}")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    ModelAndView editCause(@PathVariable("id") Integer id,
                           ModelAndView modelAndView,
                           Authentication authentication,
                           Principal principal) {
        CauseServiceModel model = causeService.findById(id);

        if (!principal.getName().equals(model.getAuthor().getEmail()) &&
                !causeService.hasAuthority(authentication, GlobalConstants.ROLE_MODERATOR)) {
            throw new AccessDeniedException("You are not authorized to access this page");
        }

        modelAndView.addObject(GlobalConstants.MODEL, model);
        addLocations(modelAndView);
        return view("causes-edit", modelAndView);
    }

    @PostMapping("/causes/form/{id}")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    ModelAndView editCauseConfirm(@PathVariable("id") Integer id,
                                  @Valid @ModelAttribute(name = GlobalConstants.MODEL)
                                          CauseFormBindingModel model,
                                  BindingResult bindingResult,
                                  Principal principal,
                                  ModelAndView modelAndView) throws IOException {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.MODEL, model);
            addLocations(modelAndView);
            return view("causes-edit", modelAndView);
        }
        causeService.edit(model, id);
        return redirect("/home");
    }

    @PageTitle("view cause")
    @GetMapping("/causes/{id}")
    ModelAndView causeDetails(@PathVariable("id") Integer id,
                              ModelAndView modelAndView,
                              Principal principal) {
        CauseServiceModel causeServiceModel = causeService.findById(id);
        CauseViewModel model = modelMapper.map(causeServiceModel, CauseViewModel.class);
        List<CommentViewModel> comments = commentService.findAll(id);

        modelAndView.addObject(GlobalConstants.MODEL, model);
        modelAndView.addObject("comments", comments);

        if (!causeServiceModel.isApproved()) {
            modelAndView.addObject("isApproved", false);
        } else {
            if (principal != null) {
                boolean isFollowing = userService.isFollowing(principal.getName(), id);
                modelAndView.addObject("isFollowing", isFollowing);
                modelAndView.addObject("isApproved", true);
            }
        }

        return view("cause-details", modelAndView);
    }

    @PageTitle("causes")
    @GetMapping("/causes")
    ModelAndView causesAll(ModelAndView modelAndView) {
        List<CauseServiceModel> causes = causeService.findAll();

        List<CauseViewModel> model = causes.stream()
                .map(c -> modelMapper.map(c, CauseViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("causes", modelAndView);
    }

    @PostMapping("/causes/approve/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView approveCause(@PathVariable("id") Integer id) {

        causeService.approve(id);
        return redirect("/home");
    }

    @PostMapping("/causes/delete/{id}")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public ModelAndView deleteCause(@PathVariable("id") Integer id,
                                    Principal principal,
                                    Authentication authentication) {

        causeService.delete(id, principal.getName(), authentication);
        return redirect("/causes");
    }

    private void addLocations(ModelAndView modelAndView) {
        modelAndView.addObject("locations", this.locationService
                .findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, LocationViewModel.class))
                .collect(Collectors.toList()));
    }

    @ExceptionHandler(CauseNotFoundException.class)
    public ModelAndView handleCauseNotFound(CauseNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}