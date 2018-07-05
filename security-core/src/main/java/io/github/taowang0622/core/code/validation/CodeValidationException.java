package io.github.taowang0622.core.code.validation;


import org.springframework.security.core.AuthenticationException;

public class CodeValidationException extends AuthenticationException {
    public CodeValidationException(String detail) {
        super(detail);
    }
}
