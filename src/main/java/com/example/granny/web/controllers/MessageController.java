package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.binding.MessageBindingModel;
import com.example.granny.domain.models.service.MessageServiceModel;
import com.example.granny.domain.models.view.MessageDetailsViewModel;
import com.example.granny.domain.models.view.MessageViewModel;
import com.example.granny.error.MessageNotFoundException;
import com.example.granny.error.ProductNotFoundException;
import com.example.granny.service.api.MessageService;
import com.example.granny.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MessageController extends BaseController {

    private final MessageService messageService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("contact")
    @GetMapping(GlobalConstants.URL_CONTACT_FORM)
    ModelAndView createMessage(ModelAndView modelAndView) {

        modelAndView.addObject(GlobalConstants.MODEL, new MessageBindingModel());
        return view("contact", modelAndView);
    }

    @PostMapping(GlobalConstants.URL_CONTACT_FORM)
    ModelAndView createMessageConfirm(@Valid @ModelAttribute(name = GlobalConstants.MODEL)
                                              MessageBindingModel model,
                                      BindingResult bindingResult,
                                      ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.MODEL, model);

            return view("contact", modelAndView);
        }
        messageService.create(modelMapper.map(model, MessageServiceModel.class));
        return view("message-sent");
    }

    @PageTitle("messages")
    @GetMapping(GlobalConstants.URL_VIEW_MESSAGES)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    ModelAndView messagesAll(ModelAndView modelAndView) {
        List<MessageServiceModel> messages = messageService.findAll();

        List<MessageViewModel> model = messages.stream()
                .map(m -> modelMapper.map(m, MessageViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("messages", modelAndView);
    }

    @PageTitle("view message")
    @GetMapping(GlobalConstants.URL_VIEW_MESSAGE_DETAILS)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    ModelAndView viewMessage(@PathVariable(name = "id") Integer id,
                             ModelAndView modelAndView,
                             HttpSession session) {
        MessageDetailsViewModel model =
                modelMapper.map(messageService.viewMessage(id), MessageDetailsViewModel.class);
        modelAndView.addObject(GlobalConstants.MODEL, model);

        int unreadMessagesSize = messageService.countUnreadMessages();
        session.setAttribute(GlobalConstants.UNREAD_MESSAGES_SIZE, unreadMessagesSize);

        return view("message-details", modelAndView);
    }

    @PostMapping(GlobalConstants.URL_DELETE_MESSAGE)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteMessage(@PathVariable("id") Integer id) {

        messageService.delete(id);
        return redirect("/messages");
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ModelAndView handleMessageNotFound(MessageNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }

}
