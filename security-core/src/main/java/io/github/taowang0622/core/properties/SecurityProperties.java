package io.github.taowang0622.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();

    private VerificationCodeProperties code = new VerificationCodeProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public VerificationCodeProperties getCode() {
        return code;
    }

    public void setCode(VerificationCodeProperties code) {
        this.code = code;
    }
}
