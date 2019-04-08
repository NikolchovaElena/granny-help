package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.binding.UserEditBindingModel;
import com.example.granny.domain.models.binding.UserPasswordBindingModel;
import com.example.granny.domain.models.binding.UserRegisterBindingModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.domain.models.view.UserViewModel;
import com.example.granny.domain.models.view.UserShowViewModel;
import com.example.granny.service.api.EventService;
import com.example.granny.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController extends BaseController {
    private static final String PASSWORDS_DONT_MATCH = "Passwords don't match";
    private static final String EMAIL_ALREADY_EXISTS = "A user with that email already exists";
    private static final String ERROR_CODE_EMAIL = "error.email";
    private static final String ERROR_CODE_CONFIRM_PASSWORD = "error.confirmPassword";

    private final UserService userService;
    private final EventService eventService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService,
                          EventService eventService, ModelMapper modelMapper) {
        this.userService = userService;
        this.eventService = eventService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(GlobalConstants.URL_USER_LOGIN)
    @PreAuthorize(GlobalConstants.IS_ANONYMOUS)
    public ModelAndView login() {
        return view("login");
    }

    @GetMapping(GlobalConstants.URL_USER_REGISTER)
    @PreAuthorize(GlobalConstants.IS_ANONYMOUS)
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.addObject(GlobalConstants.MODEL, new UserRegisterBindingModel());
        return view("register", modelAndView);
    }

    @PostMapping(GlobalConstants.URL_USER_REGISTER)
    @PreAuthorize(GlobalConstants.IS_ANONYMOUS)
    public ModelAndView registerConfirm(@Valid @ModelAttribute(name = GlobalConstants.MODEL)
                                                UserRegisterBindingModel model,
                                        BindingResult bindingResult,
                                        WebRequest request,
                                        ModelAndView modelAndView) {
        if (userService.emailExists(model.getEmail())) {
            bindingResult.rejectValue("email", ERROR_CODE_EMAIL, EMAIL_ALREADY_EXISTS);
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.MODEL, model);
            return view("register", modelAndView);
        }
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", ERROR_CODE_CONFIRM_PASSWORD, PASSWORDS_DONT_MATCH);
            modelAndView.addObject(GlobalConstants.MODEL, model);
            return view("register", modelAndView);
        }
        UserServiceModel userServiceModel = modelMapper.map(model, UserServiceModel.class);
        if (userService.register(userServiceModel)) {
            eventService.initiateRegisterVerificationEvent(userServiceModel.getEmail(), request.getLocale(), request.getContextPath());
            modelAndView.addObject("email", userServiceModel.getEmail());
            return view("registration-success", modelAndView);
        }
        return view("error");
    }

    @GetMapping(GlobalConstants.URL_USER_LOGOUT)
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return redirect(GlobalConstants.URL_INDEX);
    }

    @GetMapping("/user/edit/password")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public ModelAndView editPassword(@ModelAttribute(name = GlobalConstants.MODEL) UserPasswordBindingModel model,
                                     ModelAndView modelAndView) {
        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("edit-password", modelAndView);
    }

    @PostMapping("/user/edit/password")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public ModelAndView editPasswordConfirm(@Valid @ModelAttribute(name = GlobalConstants.MODEL) UserPasswordBindingModel model,
                                            BindingResult bindingResult,
                                            Principal principal,
                                            ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.MODEL, model);
            return view("edit-password", modelAndView);
        }
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", ERROR_CODE_CONFIRM_PASSWORD, PASSWORDS_DONT_MATCH);
            modelAndView.addObject(GlobalConstants.MODEL, model);
            return view("edit-password", modelAndView);
        }
        this.userService.edit(principal.getName(), model.getPassword(), model.getOldPassword());
        return view("profile");
    }

    @PostMapping("/user/delete")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public ModelAndView deleteProfile(Principal principal,
                                      HttpSession session) {
        userService.delete(principal.getName());
        session.invalidate();
        return redirect("/");
    }

    @GetMapping("/user/profile/{id}")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public ModelAndView profile(@PathVariable("id") Integer id,
                                ModelAndView modelAndView) {
        UserServiceModel model = this.userService.findUserById(id);
        modelAndView.addObject(GlobalConstants.MODEL, model);

        return view("profile", modelAndView);
    }

    @GetMapping("/user/edit/profile")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public ModelAndView editProfile(Principal principal,
                                    ModelAndView modelAndView) {
        UserServiceModel userServiceModel = userService.findUserByEmail(principal.getName());
        UserViewModel model = this.modelMapper.map(userServiceModel, UserViewModel.class);
        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("edit-profile", modelAndView);
    }

    @PostMapping("/user/edit/profile")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public ModelAndView editProfileConfirm(@Valid @ModelAttribute(name = GlobalConstants.MODEL) UserEditBindingModel model,
                                           BindingResult bindingResult,
                                           Principal principal,
                                           HttpSession session,
                                           ModelAndView modelAndView) throws IOException {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.MODEL, model);
            return view("edit-profile", modelAndView);
        }
        UserServiceModel userServiceModel = userService.edit(principal.getName(), model);
        UserViewModel profile = this.modelMapper.map(userServiceModel, UserViewModel.class);
        session.setAttribute(GlobalConstants.PROFILE, profile);
        return redirect("/user/profile/" + model.getId());
    }

    @GetMapping("/users/show")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView users(ModelAndView modelAndView) {
        List<UserShowViewModel> model = this.userService.findAllUsers()
                .stream()
                .map(m -> this.modelMapper.map(m, UserShowViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("users", modelAndView);
    }

    @PostMapping("/user/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteProfile(@PathVariable("id") Integer id,
                                      HttpSession session) {
        userService.delete(id);
        return redirect("/users/show");
    }


}