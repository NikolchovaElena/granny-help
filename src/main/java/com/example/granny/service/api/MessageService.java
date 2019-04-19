package com.example.granny.service.api;

import com.example.granny.domain.models.service.MessageServiceModel;

import java.util.List;

public interface MessageService {

    void create(MessageServiceModel model);

    void delete(Integer id);

    MessageServiceModel findById(Integer id);

    List<MessageServiceModel> findAll();

    MessageServiceModel viewMessage(Integer id);

    int countUnreadMessages();
}
