package io.github.taowang0622.core.code.validation.impl;

import io.github.taowang0622.core.SecurityConstants;
import io.github.taowang0622.core.code.validation.VerificationCode;
import io.github.taowang0622.core.code.validation.VerificationCodeProcessor;
import io.github.taowang0622.core.code.validation.VerificationCodeGenerator;
import io.github.taowang0622.core.code.validation.exception.CodeValidationException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @param <C> is the type of the verification code, {@link VerificationCode} or {@link io.github.taowang0622.core.code.validation.image.ImageCode}
 */
public abstract class AbastractVerificationCodeProcessor<C extends VerificationCode> implements VerificationCodeProcessor{

    /**
     * Using Spring lookup to get all beans implementing the interface "VerificationCodeGenerator"
     * In this case, there're two beans, verificationCodeSmsGenerator and imageCodeGenerator
     * Key is a string of the bean's name
     */
    @Autowired
    private Map<String, VerificationCodeGenerator> verificationCodeGenerators;

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void validate(ServletWebRequest request) throws CodeValidationException, ServletRequestBindingException {
        String SESSION_KEY = SecurityConstants.SESSION_KEY_PREFIX + StringUtils.upperCase(getProcessorType(request));

        C codeInSession = (C) sessionStrategy.getAttribute(request, SESSION_KEY);

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "verificationCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new CodeValidationException("The verification code cannot be empty");
        }

        if (codeInSession == null) {
            throw new CodeValidationException("The verification code does not exist");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, SESSION_KEY);
            throw new CodeValidationException("The verification code is expired");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new CodeValidationException("The input verification code does not match");
        }

        //Validation is successful, then remove the verification code from the session
        sessionStrategy.removeAttribute(request, SESSION_KEY);
    }

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C verificationCode = generate(request);
        store(request, verificationCode);
        send(request, verificationCode);
    }

    /**
     * Generate different kinds of code according to the request
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        String type = getProcessorType(request); //image code or sms code
        VerificationCodeGenerator verificationCodeGenerator = verificationCodeGenerators.get(type + "CodeGenerator");
        return (C) verificationCodeGenerator.createVerificationCode(request);
    }

    /**
     * Store the verification code into the session
     * @param request
     * @param verificationCode
     */
    private void store(ServletWebRequest request, C verificationCode) {
        sessionStrategy.setAttribute(request, SecurityConstants.SESSION_KEY_PREFIX + StringUtils.upperCase(getProcessorType(request)), verificationCode);
    }

    /**
     * Send the code to the client.
     * @param request
     * @param verificationCode
     */
    protected abstract void send(ServletWebRequest request, C verificationCode) throws IOException, ServletRequestBindingException;

    /*********************Helper methods****************************/

    /**
     * Get the type of the verification code based on the requested URL for generation or validation of code
     * @param request
     * @return "image" or "sms" or ""
     */
    private String getProcessorType(ServletWebRequest request) {
        String URI = request.getRequest().getRequestURI();
        if (StringUtils.startsWith(URI, SecurityConstants.DEFAULT_VERIFICATION_CODE_URL_PREFIX)) {
            return StringUtils.substringAfter(URI, "/code/");
        } else if (StringUtils.equals(URI, SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)) {
            return "image";
        } else if (StringUtils.equals(URI, SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS)) {
            return "sms";
        }
        return "";
    }
}
