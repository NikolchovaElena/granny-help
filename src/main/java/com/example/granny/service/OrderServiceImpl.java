package com.example.granny.service;

import com.example.granny.domain.entities.Order;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.binding.AddressBindingModel;
import com.example.granny.domain.models.view.CartViewModel;
import com.example.granny.domain.models.view.OrderedItemViewModel;
import com.example.granny.repository.OrderRepository;
import com.example.granny.repository.UserRepository;
import com.example.granny.service.api.OrderService;
import com.example.granny.service.api.OrderedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderedItemService orderedItemService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository, OrderedItemService orderedItemService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderedItemService = orderedItemService;
    }

    @Override
    public void create(Map<Integer, OrderedItemViewModel> products,
                       AddressBindingModel shippingDetails, String email, String notes) {
        User user = null;

        if (email != null) {
            user = this.userRepository.findByEmail(email).orElse(null);
        }
        List<OrderedItemViewModel> items = new ArrayList<>(products.values());

        Order order = new Order();
        order.setCustomer(user);
        order.setShippingDetails(shippingDetails.toString());
        order.setPriceToPay(findTotal(items));
        order.setNotes(notes);

        order = orderRepository.save(order);
        orderedItemService.save(items, order);
    }

    private BigDecimal findTotal(List<OrderedItemViewModel> items) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderedItemViewModel i : items) {
            total = total.add(i.getSum());
        }
        return total;
    }

}

