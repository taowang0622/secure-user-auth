package io.github.taowang0622.core.password.authentication;


import io.github.taowang0622.core.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Username password authentication configs.
 * This is essential for both browser and app security module, so their configuration classes should extend it.
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private AuthenticationSuccessHandler authSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authFailureHandler;

    protected void applyUsernamePasswordAuthenticationConfigs(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    //Redirecting to or returning a HTML page violates the principle of RESTful APIs!!!
                    //To work around it, need to write a controller method!!!
    //                .loginPage("/default-login-page.html")
                    //means that when needing to pass username and password, redirect uses to this URL to login!!
                    .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                    //Change the default path "/login" of UsernamePasswordAuthenticationFilter to "/authentication/form"
                    //means that Spring Security just authenticates username and password in "POST /authentication/form"!!!
                    .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                    //Log in using HTTP prompt!!
                    //This is the default config of spring security!!
                    .successHandler(authSuccessHandler)
                    .failureHandler(authFailureHandler);

    }
}
