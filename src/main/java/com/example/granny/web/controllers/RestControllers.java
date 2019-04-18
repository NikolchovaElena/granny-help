package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.Item;
import com.example.granny.domain.models.binding.CartUpdateBindingModel;
import com.example.granny.domain.models.binding.CommentBindingModel;
import com.example.granny.domain.models.service.ProductServiceModel;
import com.example.granny.domain.models.view.CartViewModel;
import com.example.granny.domain.models.view.CommentViewModel;
import com.example.granny.service.api.CauseService;
import com.example.granny.service.api.CommentService;
import com.example.granny.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestControllers extends BaseController {

    private final CommentService commentService;
    private final CauseService causeService;
    private final ProductService productService;

    @Autowired
    public RestControllers(CommentService commentService, CauseService causeService, ProductService productService) {
        this.commentService = commentService;
        this.causeService = causeService;
        this.productService = productService;
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

    //Fixme връща ли json или може и без
    @RequestMapping(value = "/delete/comment/{id}"
//            method = RequestMethod.POST,
//            produces = "application/json"
    )
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public void deleteComment(@PathVariable("id") Integer commentId) {
        commentService.delete(commentId);
    }


    @PostMapping(value = "/user/follow/causes/{id}")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public void followCause(@PathVariable("id") Integer causeId,
                            Principal principal) {
        causeService.followCause(principal.getName(), causeId);
    }

    @PostMapping("/user/unfollow/causes/{id}")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public void unfollowCause(@PathVariable("id") Integer causeId,
                              Principal principal) {
        causeService.unFollowCause(principal.getName(), causeId);
    }

    @RequestMapping(value = "/cart/add",
            method = RequestMethod.POST,
            produces = "application/json")
    public int addToCart(@RequestBody() Item model,
                         HttpSession session) {
        if (session.getAttribute(GlobalConstants.CART) == null) {
            session.setAttribute(GlobalConstants.CART, new LinkedHashMap<Integer, Integer>());
        }
        Map<Integer, Integer> products = (Map<Integer, Integer>) session.getAttribute(GlobalConstants.CART);

        products.put(model.getProductId(), model.getQuantity());
        int cartSize = products.size();

        session.setAttribute(GlobalConstants.CART, products);
        session.setAttribute("cart-size", cartSize);

        return cartSize;
    }

    @RequestMapping(value = "/cart/update",
            method = RequestMethod.POST,
            produces = "application/json")
    public void update(@RequestBody() CartUpdateBindingModel model,
                               HttpSession session) {
        Map<Integer, Integer> products = new LinkedHashMap<>();
        model.getProducts()
                .forEach(p -> products.put(p.getId(), p.getQuantity()));

        session.setAttribute(GlobalConstants.CART, products);
    }

}
