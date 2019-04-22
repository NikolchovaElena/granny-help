package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.binding.ProductBindingModel;
import com.example.granny.domain.models.service.ProductServiceModel;
import com.example.granny.domain.models.view.CartViewModel;
import com.example.granny.domain.models.view.ProductAllViewModel;
import com.example.granny.domain.models.view.ProductDetailsViewModel;
import com.example.granny.error.ProductAlreadyExistsException;
import com.example.granny.error.ProductNotFoundException;
import com.example.granny.service.api.ProductService;
import com.example.granny.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductController extends BaseController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("add product")
    @GetMapping(GlobalConstants.URL_ADD_PRODUCT)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProduct(ModelAndView modelAndView) {
        modelAndView.addObject(GlobalConstants.MODEL, new ProductBindingModel());
        return view("product-add", modelAndView);
    }

    @PostMapping(GlobalConstants.URL_ADD_PRODUCT)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProductConfirm(@Valid @ModelAttribute(name = GlobalConstants.MODEL)
                                                  ProductBindingModel model,
                                          BindingResult bindingResult,
                                          ModelAndView modelAndView) throws IOException {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.MODEL, model);
            return view("product-add", modelAndView);
        }
        this.productService.create(model);
        return redirect(GlobalConstants.URL_VIEW_PRODUCTS);
    }

    @PageTitle("products")
    @GetMapping(GlobalConstants.URL_VIEW_PRODUCTS)
    public ModelAndView allProducts(ModelAndView modelAndView) {
        modelAndView.addObject(GlobalConstants.MODEL, this.productService.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList()));

        return view(GlobalConstants.URL_VIEW_PRODUCTS, modelAndView);
    }


    @PageTitle("view product")
    @GetMapping(GlobalConstants.URL_VIEW_PRODUCT_DETAILS)
    public ModelAndView detailsProduct(@PathVariable Integer id, ModelAndView modelAndView) {
        ProductServiceModel model = productService.findById(id);

        List<ProductAllViewModel> suggested = productService.findFourRandomProducts(id)
                .stream().map(p -> modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GlobalConstants.MODEL,
                this.modelMapper.map(model, ProductDetailsViewModel.class));
        modelAndView.addObject("suggestedProducts", suggested);

        return view("product-details", modelAndView);
    }

    @PageTitle("edit product")
    @GetMapping(GlobalConstants.URL_EDIT_PRODUCT)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProduct(@PathVariable("id") Integer id,
                                    ModelAndView modelAndView) {
        ProductServiceModel model = productService.findById(id);
        modelAndView.addObject(GlobalConstants.MODEL, model);

        return view("product-edit", modelAndView);
    }

    @PostMapping(GlobalConstants.URL_EDIT_PRODUCT)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProductConfirm(@PathVariable("id") Integer id,
                                           @Valid @ModelAttribute(name = GlobalConstants.MODEL)
                                                   ProductBindingModel model,
                                           BindingResult bindingResult,
                                           ModelAndView modelAndView) throws IOException {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.MODEL, model);
            return view("product-edit", modelAndView);
        }
        productService.edit(id, model);
        return redirect("/products/" + id);
    }

    @PostMapping(GlobalConstants.URL_DELETE_PRODUCT)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProduct(@PathVariable("id") Integer id) {
        this.productService.delete(id);

        return super.redirect(GlobalConstants.URL_VIEW_PRODUCTS);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ModelAndView handleProductNotFound(ProductNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ModelAndView handleProductNameAlreadyExist(ProductAlreadyExistsException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }
}
