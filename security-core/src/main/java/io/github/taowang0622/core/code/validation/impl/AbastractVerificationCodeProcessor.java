package io.github.taowang0622.core.code.validation.impl;

import io.github.taowang0622.core.code.validation.VerificationCodeProcessor;
import io.github.taowang0622.core.code.validation.VerificationCodeGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

public abstract class AbastractVerificationCodeProcessor<C> implements VerificationCodeProcessor{

    /**
     * Using Spring lookup to get all beans implementing the interface "VerificationCodeGenerator"
     * In this case, there're two beans, verificationCodeSmsGenerator and imageCodeGenerator
     * Key is a string of the bean's name
     */
    @Autowired
    private Map<String, VerificationCodeGenerator> verificationCodeGenerators;

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

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
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request), verificationCode);
    }

    /**
     * Send the code to the client.
     * @param request
     * @param verificationCode
     */
    protected abstract void send(ServletWebRequest request, C verificationCode) throws IOException, ServletRequestBindingException;

    /*********************Helper methods****************************/

    /**
     * Get the type of the verification code based on the requested URL
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }
}
