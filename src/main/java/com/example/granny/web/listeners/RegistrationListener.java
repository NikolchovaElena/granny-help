package com.example.granny.web.listeners;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.service.api.VerificationTokenService;
import com.example.granny.web.events.OnRegistrationSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationSuccessEvent> {
    private VerificationTokenService tokenService;
    private MailSender mailSender;
    //  private MimeMessage simpleMessage = new MimeMessage(mailSession);

    @Autowired
    public RegistrationListener(VerificationTokenService tokenService,
                                MailSender mailSender) {
        this.tokenService = tokenService;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) {

        //Get the user, create verification token with an expiration date
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(event.getUser(), token);

        //Create email message
        SimpleMailMessage email = new SimpleMailMessage();
        String confirmationUrl = event.getAppUrl() + GlobalConstants.URL_CONFIRM_ACCOUNT + "?token=" + token;
        //   String message = messageSource.getMessage(, null, events.getLocale());
        email.setTo(event.getUser().getEmail());
        email.setSubject("Registration Confirmation");
        email.setText("To confirm your account, please click here : " + "http://localhost:8000" + confirmationUrl);
        mailSender.send(email);
    }
}
