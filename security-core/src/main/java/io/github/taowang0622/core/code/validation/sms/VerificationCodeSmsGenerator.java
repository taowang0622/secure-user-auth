package io.github.taowang0622.core.code.validation.sms;

import io.github.taowang0622.core.code.validation.VerificationCodeGenerator;
import io.github.taowang0622.core.code.validation.VerificationCode;
import io.github.taowang0622.core.properties.SecurityProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * SMS code is just a string of random numbers, unlike the image code has many implementations
 */
@Component("smsCodeGenerator")
public class VerificationCodeSmsGenerator implements VerificationCodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public VerificationCode createVerificationCode(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new VerificationCode(code, securityProperties.getCode().getImage().getTimeToLive());
    }
}
