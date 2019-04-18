package com.example.granny.service;

import com.example.granny.domain.entities.BillingDetails;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.binding.AddressBindingModel;
import com.example.granny.domain.models.service.AddressServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.repository.AddressRepository;
import com.example.granny.repository.UserRepository;
import com.example.granny.service.api.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void edit(BillingDetails address, AddressBindingModel model) {
        Integer id = address.getId();

        address = modelMapper.map(model, BillingDetails.class);
        address.setId(id);
        this.addressRepository.save(address);
    }

    @Override
    public AddressServiceModel findBy(UserServiceModel model) {
        return modelMapper.map(model.getBillingDetails(), AddressServiceModel.class);
    }

    @Override
    public BillingDetails addNew() {
        BillingDetails address = new BillingDetails();
        return this.addressRepository.saveAndFlush(address);
    }


}
