package io.github.taowang0622.core.code.validation;

import io.github.taowang0622.core.SecurityConstants;
import io.github.taowang0622.core.code.validation.exception.CodeValidationException;
import io.github.taowang0622.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class VerificationCodeValidationFilter extends OncePerRequestFilter implements InitializingBean {
    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    VerificationCodeProcessorHolder verificationCodeProcessorHolder;

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * A mapping from url to which code validation it should go through
     */
    private Map<String, VerificationCodeType> urlMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, VerificationCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), VerificationCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS, VerificationCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), VerificationCodeType.SMS);
    }

    /**
     * Map URLs to passed verification code type.
     *
     * @param url                  is a string of URLs separated by commas.
     * @param verificationCodeType is an enum type.
     */
    private void addUrlToMap(String url, VerificationCodeType verificationCodeType) {
        if (StringUtils.isNotBlank(url)) {
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(url, ",");
            for (String configUrl : configUrls) {
                urlMap.put(configUrl, verificationCodeType);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        VerificationCodeType type = getVerificationType(request);

        if (type != null) {
            try {
                logger.info("Validate the verification code from " + request.getRequestURI() + " and the verification code type is " + type);
                verificationCodeProcessorHolder.findVerificationCodeProcessor(type).validate(new ServletWebRequest(request, response));
                logger.info("Validation successful");
            } catch (CodeValidationException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return; //No need to go forward to next filter
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Find the verification code type mapped to the passed request
     *
     * @param request
     * @return a emum type VerificationType value or null if requested URL is not on the map.
     */
    private VerificationCodeType getVerificationType(HttpServletRequest request) {
        return urlMap.get(request.getRequestURI());
    }
}

