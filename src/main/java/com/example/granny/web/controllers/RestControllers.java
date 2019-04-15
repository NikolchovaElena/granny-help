package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.binding.CommentBindingModel;
import com.example.granny.domain.models.view.CommentViewModel;
import com.example.granny.service.api.CauseService;
import com.example.granny.service.api.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class RestControllers extends BaseController {

    private final CommentService commentService;
    private final CauseService causeService;

    @Autowired
    public RestControllers(CommentService commentService, CauseService causeService) {
        this.commentService = commentService;
        this.causeService = causeService;
    }

    @RequestMapping(value = "/causes/{id}/comments",
            method = RequestMethod.POST,
            produces = "application/json")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public CommentViewModel submitComment(@PathVariable("id") Integer id,
                                          @RequestBody() CommentBindingModel model,
                                          Principal principal) {

        return commentService.create(model.getComment(), principal.getName(), id);
    }

    @RequestMapping(value = "/delete/comment/{id}",
            method = RequestMethod.POST,
            produces = "application/json")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public void deleteComment(@PathVariable("id") Integer commentId) {
        commentService.delete(commentId);
    }


    @RequestMapping(value = "/user/follow/causes/{id}",
            method = RequestMethod.POST,
            produces = "application/json")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    void followCause(@PathVariable("id") Integer causeId,
                             Principal principal) {
        causeService.followCause(principal.getName(), causeId);
    }

    @PostMapping("/user/unfollow/causes/{id}")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    void unfollowCause(@PathVariable("id") Integer causeId,
                               Principal principal) {
        causeService.unFollowCause(principal.getName(), causeId);
    }
}
