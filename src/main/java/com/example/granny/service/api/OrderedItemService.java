package com.example.granny.service.api;

import com.example.granny.domain.entities.Order;
import com.example.granny.domain.models.view.OrderedItemViewModel;

import java.util.List;

public interface OrderedItemService {

    void save (List<OrderedItemViewModel> products, Order order);

}
