package io.github.taowang0622.core.properties.verification.code;

import io.github.taowang0622.core.properties.verification.code.VerificationCodeSmsProperties;

public class VerificationCodeImageProperties extends VerificationCodeSmsProperties {
    //default constructor
    public VerificationCodeImageProperties() {
        setLength(4);
    }

    private int width = 67;
    private int height = 23;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
