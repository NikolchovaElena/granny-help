package com.example.granny.service;

import com.example.granny.domain.entities.AddressDetails;
import com.example.granny.domain.models.binding.AddressBindingModel;
import com.example.granny.domain.models.service.AddressServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.repository.AddressDetailsRepository;
import com.example.granny.repository.UserRepository;
import com.example.granny.service.api.AddressDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressDetailsServiceImpl implements AddressDetailsService {

    private final AddressDetailsRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressDetailsServiceImpl(AddressDetailsRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void edit(AddressDetails address, AddressBindingModel model) {
        Integer id = address.getId();

        address = modelMapper.map(model, AddressDetails.class);
        address.setId(id);
        this.addressRepository.save(address);
    }

    @Override
    public AddressServiceModel findBy(UserServiceModel model) {
        return modelMapper.map(model.getBillingDetails(), AddressServiceModel.class);
    }

    @Override
    public AddressDetails addNew() {
        AddressDetails address = new AddressDetails();
        return this.addressRepository.saveAndFlush(address);
    }

}
