package com.example.granny.service.api;

import com.example.granny.domain.entities.BillingDetails;
import com.example.granny.domain.models.binding.AddressBindingModel;
import com.example.granny.domain.models.service.AddressServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;

public interface AddressService {

    void edit(BillingDetails address, AddressBindingModel model);

    AddressServiceModel findBy(UserServiceModel model);

    BillingDetails addNew();
}
