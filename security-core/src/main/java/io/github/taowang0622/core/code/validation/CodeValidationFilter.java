package io.github.taowang0622.core.code.validation;

import com.sun.org.apache.bcel.internal.classfile.Code;
import io.github.taowang0622.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CodeValidationFilter extends OncePerRequestFilter implements InitializingBean {
    private AuthenticationFailureHandler authenticationFailureHandler;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //urls that need to be authenticated using the verification code
    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    //This is from InitializingBean, and it's invoked after all properties of this bean are set(done by Spring Boot or manually)
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
        Collections.addAll(urls, configUrls);
        urls.add("/authentication/form"); //This is mandatory!!
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Specify what kind of requests we need to use the verification code to authenticate
        boolean action = false;
        for (String url : urls) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }

//        if(StringUtils.equals("/authentication/form", request.getRequestURI())
//                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")){
        if (action) {
            try {
                validate(new ServletWebRequest(request));
            } catch (CodeValidationException e) {
                //Exception Handling
                //cannot throw, because no exception handler filter is ahead of this filter
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return; //No need to go to next filter if the exception happens
            }

        }

        //No matter the request is a login request or not, the last step must be relaying to next filter
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, CodeValidationController.SESSION_KEY);

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "verificationCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new CodeValidationException("The verification code cannot be empty");
        }

        if (codeInSession == null) {
            throw new CodeValidationException("The verification code does not exist");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, CodeValidationController.SESSION_KEY);
            throw new CodeValidationException("The verification code is expired");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new CodeValidationException("The input verification code does not match");
        }

        //Validation is successful, then remove the verification code from the session
        sessionStrategy.removeAttribute(request, CodeValidationController.SESSION_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
