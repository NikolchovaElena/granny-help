package com.example.granny.service;

import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.service.api.UserService;
import com.example.granny.web.events.OnRegistrationSuccessEvent;
import com.example.granny.repository.UserRepository;
import com.example.granny.service.api.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EventServiceImpl implements EventService {

    private ApplicationEventPublisher eventPublisher;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public EventServiceImpl(ApplicationEventPublisher eventPublisher,
                            UserService userService,
                            ModelMapper modelMapper) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initiateRegisterVerificationEvent(String email, Locale locale, String appUrl) {
        User user = this.modelMapper.map(userService.findUserByEmail(email), User.class);
        eventPublisher.publishEvent(
                new OnRegistrationSuccessEvent(user, locale, appUrl));
    }


}
