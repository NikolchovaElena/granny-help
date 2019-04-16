package com.example.granny.web.controllers;

import com.example.granny.domain.models.service.ProductServiceModel;
import com.example.granny.domain.models.view.OrderViewModel;
import com.example.granny.domain.models.view.ProductDetailsViewModel;
import com.example.granny.service.api.OrderService;
import com.example.granny.service.api.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController extends BaseController {

    private final ProductService productService;
    private final OrderService orderService;
    private final ModelMapper mapper;

    @Autowired
    public OrderController(ProductService productService, OrderService orderService, ModelMapper modelMapper){
        this.productService = productService;
        this.orderService = orderService;
        this.mapper = modelMapper;
    }

    @GetMapping("order/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderProduct(@PathVariable Integer id, ModelAndView modelAndView) {
        ProductServiceModel serviceModel = productService.findById(id);
        ProductDetailsViewModel viewModel = mapper.map(serviceModel, ProductDetailsViewModel.class);
        modelAndView.addObject("product", viewModel);
        return view("order/product", modelAndView);
    }

    @GetMapping("order/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView getAllOrders(ModelAndView modelAndView) {
        List<OrderViewModel> viewModels = orderService.findAllOrders()
                .stream()
                .map(o -> mapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("orders", viewModels);
        return view("order/list-orders", modelAndView);
    }

    @GetMapping("order/customer")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getCustomerOrders(ModelAndView modelAndView, Principal principal) {
        String username = principal.getName();
        List<OrderViewModel> viewModels = orderService.findOrdersByCustomer(username)
                .stream()
                .map(o -> mapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("orders", viewModels);

        return view("order/list-orders", modelAndView);
    }
}
