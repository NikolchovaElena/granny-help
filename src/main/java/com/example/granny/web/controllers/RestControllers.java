package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.binding.CommentBindingModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.domain.models.view.CommentViewModel;
import com.example.granny.service.api.CommentService;
import com.example.granny.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class RestControllers extends BaseController {

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public RestControllers(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping(value = "/causes/{id}/comments",
            method = RequestMethod.POST,
            produces = "application/json")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public CommentViewModel submitComment(@PathVariable("id") Integer id,
                                          @RequestBody() CommentBindingModel model,
                                          Principal principal) throws Exception {

        return commentService.create(model.getComment(), principal.getName(), id);
    }
}
