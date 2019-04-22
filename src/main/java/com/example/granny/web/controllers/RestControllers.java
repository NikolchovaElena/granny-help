package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.binding.CartUpdateBindingModel;
import com.example.granny.domain.models.binding.CommentBindingModel;
import com.example.granny.domain.models.binding.OrderedItemBindingModel;
import com.example.granny.domain.models.view.CommentViewModel;
import com.example.granny.domain.models.view.OrderedItemViewModel;
import com.example.granny.domain.models.view.ProductAllViewModel;
import com.example.granny.service.api.CauseService;
import com.example.granny.service.api.CommentService;
import com.example.granny.service.api.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class RestControllers extends BaseController {

    private final CommentService commentService;
    private final CauseService causeService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public RestControllers(CommentService commentService, CauseService causeService, ProductService productService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.causeService = causeService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = GlobalConstants.URL_SUBMIT_COMMENT,
            produces = "application/json")
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public CommentViewModel submitComment(@PathVariable("id") Integer id,
                                          @RequestBody() CommentBindingModel model,
                                          Principal principal) {

        return commentService.create(model.getComment(), principal.getName(), id);
    }

    @PostMapping(value = GlobalConstants.URL_DELETE_COMMENT)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public void deleteComment(@PathVariable("id") Integer commentId) {

        commentService.delete(commentId);
    }

    @PostMapping(value = GlobalConstants.URL_FOLLOW_CAUSE)
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public void followCause(@PathVariable("id") Integer causeId,
                            Principal principal) {
        causeService.followCause(principal.getName(), causeId);
    }

    @PostMapping(GlobalConstants.URL_UNFOLLOW_CAUSE)
    @PreAuthorize(GlobalConstants.IS_AUTHENTICATED)
    public void unfollowCause(@PathVariable("id") Integer causeId,
                              Principal principal) {
        causeService.unFollowCause(principal.getName(), causeId);
    }

    @PostMapping(value = GlobalConstants.URL_ADD_TO_CART,
            produces = "application/json")
    public int addToCart(@RequestBody() OrderedItemBindingModel model,
                         HttpSession session) {
        if (session.getAttribute(GlobalConstants.CART) == null) {
            session.setAttribute(GlobalConstants.CART, new LinkedHashMap<Integer, OrderedItemViewModel>());
        }
        Map<Integer, OrderedItemViewModel> products = (Map<Integer, OrderedItemViewModel>) session.getAttribute(GlobalConstants.CART);

        ProductAllViewModel p = modelMapper.map(productService.findById(model.getProductId()), ProductAllViewModel.class);
        products.put(model.getProductId(), new OrderedItemViewModel(p, model.getQuantity()));
        int cartSize = products.size();

        session.setAttribute(GlobalConstants.CART, products);
        session.setAttribute(GlobalConstants.CART_SIZE, cartSize);

        return cartSize;
    }

    @PostMapping(value = GlobalConstants.URL_UPDATE_CART)
    public void updateCart(@RequestBody() CartUpdateBindingModel model,
                           HttpSession session) {

        Map<Integer, OrderedItemViewModel> products = new LinkedHashMap<>();

        model.getProducts()
                .forEach(entry -> {
                    ProductAllViewModel p = modelMapper.map(productService.findById(entry.getId()), ProductAllViewModel.class);
                    products.put(entry.getId(), new OrderedItemViewModel(p, entry.getQuantity()));
                });

        session.setAttribute(GlobalConstants.CART, products);
    }

}
