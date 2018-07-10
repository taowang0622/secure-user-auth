package io.github.taowang0622.core.code.validation.config;

import io.github.taowang0622.core.SecurityConstants;
import io.github.taowang0622.core.code.validation.VerificationCodeGenerator;
import io.github.taowang0622.core.code.validation.image.VerificationCodeImageGenerator;
import io.github.taowang0622.core.code.validation.sms.DefaultSmsProvider;
import io.github.taowang0622.core.code.validation.sms.SmsProvider;
import io.github.taowang0622.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VerificationCodeGeneratorBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean(SecurityConstants.DEFAULT_VERIFICATION_CODE_GENERATOR_FORM)
    @ConditionalOnMissingBean(name = SecurityConstants.DEFAULT_VERIFICATION_CODE_GENERATOR_FORM)
    public VerificationCodeGenerator imageCodeGenerator() {
        VerificationCodeImageGenerator verificationCodeImageGenerator = new VerificationCodeImageGenerator();
        verificationCodeImageGenerator.setSecurityProperties(securityProperties);
        return verificationCodeImageGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsProvider.class)
    public SmsProvider smsProvider() {
        return new DefaultSmsProvider();
    }
}
