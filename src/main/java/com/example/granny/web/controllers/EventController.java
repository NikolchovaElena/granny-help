package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.VerificationToken;
import com.example.granny.repository.VerificationTokenRepository;
import com.example.granny.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;

//TODO да сложа абстракция, да не ползвам направо токен репозиторито
@Controller
public class EventController extends BaseController {
    private UserService userService;
    private VerificationTokenRepository tokenRepository;

    @Autowired
    public EventController(UserService userService,
                           VerificationTokenRepository tokenRepository) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    @GetMapping(GlobalConstants.URL_CONFIRM_ACCOUNT)
    public ModelAndView confirmUserAccount(@RequestParam("token") String verificationToken,
                                           ModelAndView modelAndView) {
        VerificationToken token = tokenRepository.findByToken(verificationToken);

        //TODO add errors to global exceptions
        //TODO move business logic to service, check there and throw exceptions
        if (token == null) {
            String message = "The link is invalid or broken!";
            modelAndView.addObject("error", message);
            modelAndView.setViewName("redirect:/error");
            return modelAndView;
        }

        Calendar calendar = Calendar.getInstance();

        if ((token.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            String message = "The link has expired";
            modelAndView.addObject("error", message);
            modelAndView.setViewName("redirect:/error");
        }

        userService.enableUser(token.getUser());
        return redirect(GlobalConstants.URL_ACCOUNT_VERIFIED);
    }

    @GetMapping(GlobalConstants.URL_ACCOUNT_VERIFIED)
    public ModelAndView accountVerification() {
        return view("account-verified");
    }


}
