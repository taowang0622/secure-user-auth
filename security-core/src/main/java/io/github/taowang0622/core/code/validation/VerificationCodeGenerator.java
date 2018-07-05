package io.github.taowang0622.core.code.validation;

import org.springframework.web.context.request.ServletWebRequest;

public interface VerificationCodeGenerator {
    public ImageCode createVerificationCode(ServletWebRequest request);
}
