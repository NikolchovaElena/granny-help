package com.example.granny.service.api;

import com.example.granny.domain.models.binding.AddressBindingModel;
import com.example.granny.domain.models.view.OrderedItemViewModel;

import java.util.Map;

public interface OrderService {

    void create(Map<Integer, OrderedItemViewModel> products,
                AddressBindingModel shippingDetails, String email, String notes);

}
