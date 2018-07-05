package io.github.taowang0622.core.properties;

public class VerificationCodeProperties {
    private VerificationCodeImageProperties image = new VerificationCodeImageProperties();

    public VerificationCodeImageProperties getImage() {
        return image;
    }

    public void setImage(VerificationCodeImageProperties image) {
        this.image = image;
    }
}
