package com.example.granny.service;

import com.example.granny.domain.entities.Order;
import com.example.granny.domain.entities.Product;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.service.OrderServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.repository.OrderRepository;
import com.example.granny.repository.ProductRepository;
import com.example.granny.service.api.OrderService;
import com.example.granny.service.api.UserService;
import com.example.granny.validation.api.ProductValidationService;
import com.example.granny.validation.api.UserValidationService;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ModelMapper mapper;
    private final UserValidationService userValidation;
    private final ProductValidationService productValidation;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserService userService,
            UserValidationService userValidation,
            ProductValidationService productValidation,
            ModelMapper mapper
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.userValidation = userValidation;
        this.productValidation = productValidation;
        this.mapper = mapper;
    }

    @Override
    public void createOrder(Integer productId, String email) throws Exception {
        UserServiceModel userModel = userService.findUserByEmail(email);
        if(!userValidation.isValid(userModel)) {
            throw new Exception();
        }

        Product product = productRepository.findById(productId)
                .filter(productValidation::isValid)
                .orElseThrow(Exception::new);

        User user = new User();
        user.setId(userModel.getId());
        Order order = new Order();
        order.setProduct(product);
        order.setUser(user);

        orderRepository.save(order);
    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(o -> mapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findOrdersByCustomer(String username) {
        return orderRepository.findAllByUser_Email(username)
                .stream()
                .map(o -> mapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }
}
