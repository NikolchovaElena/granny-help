package com.example.granny.service;

import com.example.granny.domain.entities.Order;
import com.example.granny.domain.entities.OrderedItem;
import com.example.granny.domain.entities.Product;
import com.example.granny.domain.models.view.OrderedItemViewModel;
import com.example.granny.repository.OrderedItemRepository;
import com.example.granny.service.api.OrderedItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderedItemServiceImpl implements OrderedItemService {

    private final OrderedItemRepository orderedItemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderedItemServiceImpl(OrderedItemRepository orderedItemRepository, ModelMapper modelMapper) {
        this.orderedItemRepository = orderedItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(List<OrderedItemViewModel> products, Order order) {
        products.forEach(p -> {
            OrderedItem item = new OrderedItem();

            item.setProduct(modelMapper.map(p.getProduct(), Product.class));
            item.setQuantity(p.getQuantity());
            item.setSum(p.getSum());
            item.setOrder(order);

            orderedItemRepository.save(item);
        });
    }
}
