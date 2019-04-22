package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.binding.AddressBindingModel;
import com.example.granny.domain.models.service.AddressServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.domain.models.view.OrderedItemViewModel;
import com.example.granny.service.api.AddressDetailsService;
import com.example.granny.service.api.OrderService;
import com.example.granny.service.api.ProductService;
import com.example.granny.service.api.UserService;
import com.example.granny.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;

@Controller
public class CartController extends BaseController {

    private final ProductService productService;
    private final AddressDetailsService addressDetailsService;
    private final OrderService orderService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CartController(ProductService productService, AddressDetailsService addressDetailsService, OrderService orderService, UserService userService, ModelMapper modelMapper) {
        this.productService = productService;
        this.addressDetailsService = addressDetailsService;
        this.orderService = orderService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("cart")
    @GetMapping(GlobalConstants.URL_VIEW_CART)
    public ModelAndView viewCart(HttpSession session,
                                 ModelAndView modelAndView) {
        Map<Integer, OrderedItemViewModel> products =
                (Map<Integer, OrderedItemViewModel>) session.getAttribute(GlobalConstants.CART);
        if (products == null || products.size() == 0) {
            return view("empty-cart");
        }

        viewCart(products, modelAndView);
        return view("cart", modelAndView);
    }

    @GetMapping(GlobalConstants.URL_DELETE_ITEM_FROM_CART)
    public ModelAndView deleteItem(@PathVariable Integer id,
                                   HttpSession session,
                                   ModelAndView modelAndView) {
        Map<Integer, OrderedItemViewModel> products =
                (Map<Integer, OrderedItemViewModel>) session.getAttribute(GlobalConstants.CART);
        products.remove(id);
        session.setAttribute(GlobalConstants.CART, products);
        session.setAttribute(GlobalConstants.CART_SIZE, products.size());

        viewCart(products, modelAndView);

        return redirect(GlobalConstants.URL_VIEW_CART);
    }

    @PageTitle("checkout")
    @GetMapping(GlobalConstants.URL_CART_CHECKOUT)
    public ModelAndView checkout(HttpSession session,
                                 Principal principal,
                                 ModelAndView modelAndView) {
        Map<Integer, OrderedItemViewModel> products =
                (Map<Integer, OrderedItemViewModel>) session.getAttribute(GlobalConstants.CART);
        viewCart(products, modelAndView);

        AddressBindingModel model = new AddressBindingModel();

        if (principal != null) {
            UserServiceModel userServiceModel = userService.findUserByEmail(principal.getName());
            AddressServiceModel addressServiceModel = addressDetailsService.findBy(userServiceModel);
            model = this.modelMapper.map(addressServiceModel, AddressBindingModel.class);
        }
        modelAndView.addObject(GlobalConstants.ADDRESS, model);

        return view("checkout", modelAndView);
    }

    @PostMapping(GlobalConstants.URL_CART_CHECKOUT)
    public ModelAndView placeOrder(@Valid @ModelAttribute(GlobalConstants.ADDRESS)
                                           AddressBindingModel model,
                                   BindingResult bindingResult,
                                   @ModelAttribute("notes")
                                           String notes,
                                   HttpSession session,
                                   Principal principal,
                                   ModelAndView modelAndView) {
        Map<Integer, OrderedItemViewModel> products =
                (Map<Integer, OrderedItemViewModel>) session.getAttribute(GlobalConstants.CART);
        viewCart(products, modelAndView);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.ADDRESS, model);
            return view("checkout", modelAndView);
        }
        String email = principal != null ? principal.getName() : null;

        orderService.create(products, model, email, notes);
        session.removeAttribute(GlobalConstants.CART);
        session.removeAttribute(GlobalConstants.CART_SIZE);

        return view("thank-you");
    }

    private void viewCart(Map<Integer, OrderedItemViewModel> products, ModelAndView modelAndView) {

        BigDecimal total = productService.findTotal(products);

        modelAndView.addObject(GlobalConstants.MODEL, products.values());
        modelAndView.addObject("total", total);
    }
}
