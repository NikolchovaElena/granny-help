package com.example.granny.web.controllers;

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

    @PostMapping("/users/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable Integer id) {
        this.userService.setUserRole(id, "user");

        return super.redirect("/users");
    }

    @PostMapping("/users/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable Integer id) {
        this.userService.setUserRole(id, "moderator");

        return super.redirect("/users");
    }

    @PostMapping("/users/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable Integer id) {
        this.userService.setUserRole(id, "admin");

        return super.redirect("/users");
    }
}
