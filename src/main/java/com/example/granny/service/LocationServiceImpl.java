package com.example.granny.service;

import com.example.granny.domain.entities.Location;
import com.example.granny.domain.models.service.LocationServiceModel;
import com.example.granny.repository.LocationRepsitory;
import com.example.granny.service.api.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepsitory locationRepsitory;
    private final ModelMapper modelMapper;

    @Autowired
    public LocationServiceImpl(LocationRepsitory locationRepsitory, ModelMapper modelMapper) {
        this.locationRepsitory = locationRepsitory;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<LocationServiceModel> findAll() {
        List<Location> locations = locationRepsitory.findAll();

        return locations.stream()
                .map(l -> modelMapper.map(l, LocationServiceModel.class))
                .collect(Collectors.toList());
    }
}
