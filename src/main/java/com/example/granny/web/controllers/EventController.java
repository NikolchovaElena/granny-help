package com.example.granny.web.controllers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.VerificationToken;
import com.example.granny.error.LinkHasExpired;
import com.example.granny.repository.VerificationTokenRepository;
import com.example.granny.service.api.UserService;
import com.example.granny.service.api.VerificationTokenService;
import com.example.granny.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;

@Controller
public class EventController extends BaseController {
    private UserService userService;

    @Autowired
    public EventController(UserService userService) {
        this.userService = userService;
    }

    @PageTitle("confirm")
    @GetMapping(GlobalConstants.URL_CONFIRM_ACCOUNT)
    public ModelAndView confirmUserAccount(@RequestParam("token") String verificationToken) {
       userService.confirmAccount(verificationToken);

        return redirect(GlobalConstants.URL_ACCOUNT_VERIFIED);
    }

    @PageTitle("verified")
    @GetMapping(GlobalConstants.URL_ACCOUNT_VERIFIED)
    public ModelAndView accountVerification() {
        return view("account-verified");
    }

    @ExceptionHandler(LinkHasExpired.class)
    public ModelAndView handleLinkHasExpired(LinkHasExpired e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }
}
