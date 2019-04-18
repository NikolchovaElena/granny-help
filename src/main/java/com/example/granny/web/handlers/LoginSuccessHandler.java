package com.example.granny.web.handlers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.Item;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.view.UserViewModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {

        //Add user info to the session
        HttpSession session = httpServletRequest.getSession();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserViewModel profile = new UserViewModel(user.getFirstName(), user.getLastName(),
                user.getImageUrl(), user.getAbout());

        session.setAttribute(GlobalConstants.PROFILE, profile);

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.sendRedirect(GlobalConstants.URL_USER_HOME);
    }
}
