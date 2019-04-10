package com.example.granny.service.api;

import com.example.granny.domain.entities.Location;
import com.example.granny.domain.models.service.LocationServiceModel;

import java.util.List;

public interface LocationService {
    List<LocationServiceModel> findAll();
}
