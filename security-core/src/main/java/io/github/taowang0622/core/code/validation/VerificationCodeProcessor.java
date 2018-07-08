package io.github.taowang0622.core.code.validation;

import org.springframework.web.context.request.ServletWebRequest;


public interface VerificationCodeProcessor {
    /**
     * Common key prefix for different kinds of verification code when adding to session
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    void create(ServletWebRequest request) throws Exception;
}
