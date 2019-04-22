package com.example.granny.service.api;

import com.example.granny.domain.entities.AddressDetails;
import com.example.granny.domain.models.binding.AddressBindingModel;
import com.example.granny.domain.models.service.AddressServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;

public interface AddressDetailsService {

    void edit(AddressDetails address, AddressBindingModel model);

    AddressServiceModel findBy(UserServiceModel model);

    AddressDetails addNew();
}
