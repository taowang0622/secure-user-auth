package io.github.taowang0622.core.code.validation;

import io.github.taowang0622.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VerificationCodeGeneratorBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "verificationCodeImageGenerator")
    public VerificationCodeGenerator verificationCodeImageGenerator() {
        VerificationCodeImageGenerator verificationCodeImageGenerator = new VerificationCodeImageGenerator();
        verificationCodeImageGenerator.setSecurityProperties(securityProperties);
        return verificationCodeImageGenerator;
    }
}
