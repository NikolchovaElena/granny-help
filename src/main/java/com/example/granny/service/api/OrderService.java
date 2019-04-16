package com.example.granny.service.api;
import com.example.granny.domain.models.service.OrderServiceModel;

import java.util.List;

public interface OrderService {

    void createOrder(Integer productId, String email) throws Exception;

    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findOrdersByCustomer(String username);
}
