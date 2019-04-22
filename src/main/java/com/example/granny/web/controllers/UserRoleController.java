package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserRoleController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserRoleController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(GlobalConstants.URL_SET_ROLE_USER)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable Integer id) {
        this.userService.setUserRole(id, "user");

        return super.redirect(GlobalConstants.URL_VIEW_USERS);
    }

    @PostMapping(GlobalConstants.URL_ROLE_USER_MODERATOR)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable Integer id) {
        this.userService.setUserRole(id, "moderator");

        return super.redirect(GlobalConstants.URL_VIEW_USERS);
    }

    @PostMapping(GlobalConstants.URL_SET_ROLE_ADMIN)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable Integer id) {
        this.userService.setUserRole(id, "admin");

        return super.redirect(GlobalConstants.URL_VIEW_USERS);
    }
}
