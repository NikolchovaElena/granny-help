package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.binding.MessageBindingModel;
import com.example.granny.domain.models.service.MessageServiceModel;
import com.example.granny.domain.models.view.MessageDetailsViewModel;
import com.example.granny.domain.models.view.MessageViewModel;
import com.example.granny.service.api.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/contact/form")
    ModelAndView createMessage(ModelAndView modelAndView) {

        modelAndView.addObject(GlobalConstants.MODEL, new MessageBindingModel());
        return view("contact", modelAndView);
    }

    @PostMapping("/contact/form")
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

    @GetMapping("/messages")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    ModelAndView messagesAll(ModelAndView modelAndView) {
        List<MessageServiceModel> messages = messageService.findAll();

        List<MessageViewModel> model = messages.stream()
                .map(m -> modelMapper.map(m, MessageViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("messages", modelAndView);
    }

    @GetMapping("/messages/{id}")
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

    @PostMapping("/messages/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCause(@PathVariable("id") Integer id) {

        messageService.delete(id);
        return redirect("/messages");
    }

}
