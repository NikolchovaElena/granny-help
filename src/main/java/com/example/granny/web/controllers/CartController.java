package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.binding.AddressBindingModel;
import com.example.granny.domain.models.service.AddressServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.domain.models.view.CartViewModel;
import com.example.granny.service.api.AddressService;
import com.example.granny.service.api.ProductService;
import com.example.granny.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class CartController extends BaseController {

    private final ProductService productService;
    private final AddressService addressService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CartController(ProductService productService, AddressService addressService, UserService userService, ModelMapper modelMapper) {
        this.productService = productService;
        this.addressService = addressService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/cart")
    public ModelAndView viewCart(HttpSession session,
                                 ModelAndView modelAndView) {
        Map<Integer, Integer> products = (Map<Integer, Integer>) session.getAttribute(GlobalConstants.CART);
        if (products == null) {
            return view("empty-cart");
        }

        viewCart(products, modelAndView);
        return view("cart", modelAndView);
    }

    @GetMapping("/cart/delete/{id}")
    public ModelAndView deleteItem(@PathVariable Integer id,
                                   HttpSession session,
                                   ModelAndView modelAndView) {
        Map<Integer, Integer> products = (Map<Integer, Integer>) session.getAttribute(GlobalConstants.CART);
        products.remove(id);
        session.setAttribute(GlobalConstants.CART, products);
        session.setAttribute("cart-size", products.size());

        viewCart(products, modelAndView);
        return view("cart", modelAndView);
    }

    @GetMapping("/cart/checkout")
    public ModelAndView checkout(HttpSession session,
                                 Principal principal,
                                 ModelAndView modelAndView) {
        Map<Integer, Integer> products = (Map<Integer, Integer>) session.getAttribute(GlobalConstants.CART);
        viewCart(products, modelAndView);

        AddressBindingModel model = new AddressBindingModel();

        if (principal != null) {
            UserServiceModel userServiceModel = userService.findUserByEmail(principal.getName());
            AddressServiceModel addressServiceModel = addressService.findBy(userServiceModel);
            model = this.modelMapper.map(addressServiceModel, AddressBindingModel.class);
        }
        modelAndView.addObject("address",model);

        return view("checkout", modelAndView);
    }

    @GetMapping("/order/success")
    public ModelAndView placeOrder(HttpSession session,
                                   Principal principal,
                                   ModelAndView modelAndView) {
        Map<Integer, Integer> products = (Map<Integer, Integer>) session.getAttribute(GlobalConstants.CART);


        // ...
        return view("thank-you");
    }

    private void viewCart(Map<Integer, Integer> products, ModelAndView modelAndView) {

        List<CartViewModel> model = productService.findAll(products);

        BigDecimal total = productService.findTotal(model);

        modelAndView.addObject(GlobalConstants.MODEL, model);
        modelAndView.addObject("total", total);
    }


}
