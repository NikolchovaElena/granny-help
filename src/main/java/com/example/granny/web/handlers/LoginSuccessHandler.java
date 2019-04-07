package com.example.granny.web.handlers;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {

        HttpSession session = httpServletRequest.getSession();
       // User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      //  session.setAttribute(GlobalConstants.MODEL, user);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.sendRedirect(GlobalConstants.URL_USER_HOME);
    }
}
