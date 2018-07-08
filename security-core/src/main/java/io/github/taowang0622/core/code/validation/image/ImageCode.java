package io.github.taowang0622.core.code.validation.image;

import io.github.taowang0622.core.code.validation.VerificationCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Represents a verification code in the format of image
 */
public class ImageCode extends VerificationCode {
    private BufferedImage image;
    /**
     *
     * @param image
     * @param code
     * @param timeToLive is in unites of seconds
     */
    public ImageCode(BufferedImage image, String code, int timeToLive) {
        super(code, timeToLive);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expirationTime) {
        super(code, expirationTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
