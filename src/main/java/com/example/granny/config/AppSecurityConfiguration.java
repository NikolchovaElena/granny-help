package com.example.granny.config;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.web.handlers.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/css/**",
                        "/js/**",
                        "/fonts/**",
                        "/vendor/**",
                        "/images/**").permitAll()
                .antMatchers(
                        GlobalConstants.URL_ABOUT,
                        GlobalConstants.URL_INDEX,
                        GlobalConstants.URL_USER_HOME,
                        "/causes/**",
                        "/products/**",
                        "/contact/form",
                        "/cart/**",
                        "/order/success",
                        "/delete/comment/**").permitAll()
                .antMatchers(
                        GlobalConstants.URL_USER_LOGIN,
                        GlobalConstants.URL_USER_REGISTER,
                        GlobalConstants.URL_CONFIRM_ACCOUNT,
                        GlobalConstants.URL_ACCOUNT_VERIFIED).anonymous()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(GlobalConstants.URL_USER_LOGIN)
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(myAuthenticationSuccessHandler())
                .and()
                .logout()
                .logoutSuccessUrl(GlobalConstants.URL_INDEX);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new LoginSuccessHandler();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");

        return repository;
    }
}
