package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.domain.models.view.CauseViewModel;
import com.example.granny.domain.models.view.UserViewModel;
import com.example.granny.service.api.CauseService;
import com.example.granny.service.api.MessageService;
import com.example.granny.service.api.UserService;
import com.example.granny.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final UserService userService;
    private final CauseService causeService;
    private final MessageService messageService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(UserService userService, CauseService causeService, MessageService messageService, ModelMapper modelMapper) {
        this.userService = userService;
        this.causeService = causeService;
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }
    @PageTitle("index")
    @GetMapping(GlobalConstants.URL_INDEX)
    public ModelAndView index() {
        return view("index");
    }

    @PageTitle("home")
    @GetMapping(GlobalConstants.URL_USER_HOME)
    public ModelAndView home(Principal principal,
                             Authentication authentication,
                             ModelAndView modelAndView,
                             HttpSession session) {

        if (principal == null) {
            return view("index");
        }
        UserServiceModel user = userService.findUserByEmail(principal.getName());

        List<CauseServiceModel> approvedServiceModel = causeService.findAllApproved(user.getId());
        List<CauseServiceModel> pendingServiceModel = causeService.findAllPending(user.getId(), authentication);
        List<CauseServiceModel> pinnedServiceModel = userService.findAllPinned(user.getId());

        modelAndView.addObject("approved", fetchCauses(approvedServiceModel));
        modelAndView.addObject("pending", fetchCauses(pendingServiceModel));
        modelAndView.addObject("pinned", fetchCauses(pinnedServiceModel));

        if (causeService.hasAuthority(authentication,GlobalConstants.ROLE_MODERATOR)) {
            int unreadMessagesSize = messageService.countUnreadMessages();
            session.setAttribute(GlobalConstants.UNREAD_MESSAGES_SIZE, unreadMessagesSize);
        }

        return view("home", modelAndView);
    }

    @PageTitle("about")
    @GetMapping(GlobalConstants.URL_ABOUT)
    public ModelAndView about(ModelAndView modelAndView) {
        List<UserViewModel> model = userService.findFourRandomUsers()
                .stream()
                .map(u -> modelMapper.map(u, UserViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GlobalConstants.MODEL, model);
        return view("about", modelAndView);
    }

    private List<CauseViewModel> fetchCauses(List<CauseServiceModel> models) {
        return models.stream()
                .map(c -> modelMapper.map(c, CauseViewModel.class))
                .collect(Collectors.toList());
    }
}
