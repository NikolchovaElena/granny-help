package com.example.granny.service;

import com.example.granny.domain.entities.Message;
import com.example.granny.domain.models.service.MessageServiceModel;
import com.example.granny.error.MessageNotFoundException;
import com.example.granny.repository.MessageRepository;
import com.example.granny.service.api.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    static final MessageNotFoundException MESSAGE_NOT_FOUND =
            new MessageNotFoundException("The message you requested could not be found");

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(MessageServiceModel model) {
        Message message = modelMapper.map(model, Message.class);
        messageRepository.saveAndFlush(message);
    }

    @Override
    public void delete(Integer id) {
        Message message = messageRepository.findById(id).orElseThrow(
                () -> MESSAGE_NOT_FOUND);
        messageRepository.delete(message);
    }

    @Override
    public MessageServiceModel findById(Integer id) {
        Message message = messageRepository.findById(id).orElseThrow(
                () -> MESSAGE_NOT_FOUND);
        return modelMapper.map(message, MessageServiceModel.class);
    }

    @Override
    public List<MessageServiceModel> findAll() {
        List<Message> messages = messageRepository.findAllByOrderByDateAsc();
        return messages.stream()
                .map(m -> modelMapper.map(m, MessageServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public MessageServiceModel getOpenedMessage(Integer id) {
        Message message = messageRepository.findById(id).orElseThrow(
                () -> MESSAGE_NOT_FOUND);

        if (!message.isOpen()) {
            message.setOpen(true);
            message = messageRepository.saveAndFlush(message);
        }
        return modelMapper.map(message, MessageServiceModel.class);
    }
}
