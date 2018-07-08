package io.github.taowang0622.core.code.validation;

import io.github.taowang0622.core.code.validation.VerificationCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface VerificationCodeGenerator {
    public VerificationCode createVerificationCode(ServletWebRequest request);
}
