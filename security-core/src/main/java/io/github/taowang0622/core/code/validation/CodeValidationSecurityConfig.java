package io.github.taowang0622.core.code.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class CodeValidationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private VerificationCodeValidationFilter verificationCodeValidationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(verificationCodeValidationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
