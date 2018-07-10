package io.github.taowang0622.core.code.validation;

import io.github.taowang0622.core.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VerificationCodeProcessorHolder {
    @Autowired
    private Map<String, VerificationCodeProcessor> verificationCodeProcessors;

    /**
     * Search the verification code processor bean according to the passed verification code type
     * @param type is a value of the enum type {@link VerificationCodeType}
     * @return an instance of the interface {@link VerificationCodeProcessor} or null if not found
     */
    public VerificationCodeProcessor findVerificationCodeProcessor(VerificationCodeType type) {
        return verificationCodeProcessors.get(StringUtils.lowerCase(type.toString()) + SecurityConstants.DEFAULT_VERIFICATION_CODE_PROCESSOR_SUFFIX);
    }
}
