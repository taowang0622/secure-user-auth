package io.github.taowang0622.browser.config;

import io.github.taowang0622.core.code.validation.CodeValidationFilter;
import io.github.taowang0622.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        //BCryptPasswordEncoder is an implementation of PasswordEncoder that uses the BCrypt strong hashing function.
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler authSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authFailureHandler;

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui --
            // Playing around with REST endpoints in swagger-ui.html should be protected!!
//            "/swagger-resources/**",
//            "/swagger-ui.html",
//            "/v2/api-docs",
//            "/webjars/**",

            "/authentication/require",
            "/code/image"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CodeValidationFilter codeValidationFilter = new CodeValidationFilter();
        codeValidationFilter.setAuthenticationFailureHandler(authFailureHandler);
        codeValidationFilter.setSecurityProperties(securityProperties);
        codeValidationFilter.afterPropertiesSet();

        //Log in using <form></form>!!
        http
                .addFilterBefore(codeValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                //Redirecting to/Returning a HTML page violates the principle of RESTful APIs!!!
                //To work around it, need to write a controller method!!!
//                .loginPage("/default-login-page.html")
                //means that when needing to pass username and password, redirect uses to this URL to login!!
                .loginPage("/authentication/require")
                //Change the default path "/login" of UsernamePasswordAuthenticationFilter to "/authentication/form"
                //means that Spring Security just authenticates username and password in "POST /authentication/form"!!!
                .loginProcessingUrl("/authentication/form")
                //Log in using HTTP prompt!!
                //This is the default config of spring security!!
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
//        http.httpBasic()
                .and()
                //By default, accessing all URLs is required to authenticate.
                //By using authorizeRequests, we can customize authenticating or not authenticating specific URLs
                //We can even add more specific restrictions to URLs, like only admin can access "/admin/**", etc!!
                .authorizeRequests()
                //Note that loginPage controller method configured above and the login HTML page URL redirected don't need to authenticate!!
                //Allow anyone (including unauthenticated users) to access to matched URLs.
                .antMatchers(securityProperties.getBrowser().getLoginPage()).permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                //Any URL that starts with “/admin/” must be an administrative user.
//                .antMatchers("/admin/**").hasRole("ADMIN")
                //All remaining URLs require that the user be successfully authenticated
                .anyRequest().authenticated()
                .and()
                //csrf() provides configurations on Cross-Site Request Forgery(CSRF) Protection!!
                .csrf().disable();
    }
}
