package io.github.taowang0622.core.properties.verification.code;

import io.github.taowang0622.core.properties.verification.code.VerificationCodeImageProperties;
import io.github.taowang0622.core.properties.verification.code.VerificationCodeSmsProperties;

public class VerificationCodeProperties {
    private VerificationCodeImageProperties image = new VerificationCodeImageProperties();

    private VerificationCodeSmsProperties sms = new VerificationCodeSmsProperties();

    public VerificationCodeImageProperties getImage() {
        return image;
    }

    public void setImage(VerificationCodeImageProperties image) {
        this.image = image;
    }

    public VerificationCodeSmsProperties getSms() {
        return sms;
    }

    public void setSms(VerificationCodeSmsProperties sms) {
        this.sms = sms;
    }
}
