package com.example.granny.web.controllers;

import com.example.granny.domain.models.binding.CommentBindingModel;
import com.example.granny.domain.models.service.CommentServiceModel;
import com.example.granny.domain.models.view.CommentViewModel;
import com.example.granny.service.api.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/causes/{id}/comments",
            method = RequestMethod.POST,
            produces = "application/json")
    public CommentViewModel submitComment(@PathVariable("id") Integer id,
                                          @RequestBody() CommentBindingModel model,
                                          Principal principal) throws Exception {

        return commentService.create(model.getComment(), principal.getName(), id);
    }
}
