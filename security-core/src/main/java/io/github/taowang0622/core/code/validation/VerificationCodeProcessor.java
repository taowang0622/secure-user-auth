package io.github.taowang0622.core.code.validation;

import io.github.taowang0622.core.code.validation.exception.CodeValidationException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * The instance for creating or validating verification code.
 */
public interface VerificationCodeProcessor {
    /**
     * Create, store and send an image or SMS verification code according to the request
     * @param request is a wrapper for an HttpServletRequest object and an HttpServletResponse object
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * Validate the request with a verification code that can be of type image or sms
     * @param request is a wrapper for an HttpServletRequest object and an HttpServletResponse object
     * @throws CodeValidationException
     */
    void validate(ServletWebRequest request) throws CodeValidationException, ServletRequestBindingException;
}
