package io.github.taowang0622.core.sms.authentication;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmsCodeAuthenticationFilter extends
        AbstractAuthenticationProcessingFilter {
    // ~ Static fields/initializers
    // =====================================================================================

    public static final String SPRING_SECURITY_SMS_KEY= "phone-number";

    private String smsParameter = SPRING_SECURITY_SMS_KEY;
    private boolean postOnly = true;

    // ~ Constructors
    // ===================================================================================================

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/authentication/sms", "POST"));
    }

    // ~ Methods
    // ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String phoneNumber = obtainPhoneNumber(request);

        if (phoneNumber == null) {
            phoneNumber = "";
        }

        phoneNumber = phoneNumber.trim();

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(phoneNumber);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    protected String obtainPhoneNumber(HttpServletRequest request) {
        return request.getParameter(smsParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request,
                              SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param usernameParameter the parameter name. Defaults to "username".
     */
    public void setSmsParameter(String smsParameter) {
        Assert.hasText(smsParameter, "phone-number parameter must not be empty or null");
        this.smsParameter = smsParameter;
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getSmsParameter() {
        return smsParameter;
    }

}

