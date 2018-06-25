package io.github.taowang0622.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.taowang0622.core.properties.LoginType;
import io.github.taowang0622.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
//public class AuthSuccessHandler implements AuthenticationSuccessHandler {
//SimpleUrlAuthenticationSuccessHandler is Spring Security default auth success handler that supports redirection!!!
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    //Authentication is one of core interfaces in Spring Security and UserDetails object from MyUserDetailsService is encapsulated into it!!
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("Login success");

        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            //redirection!!
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
