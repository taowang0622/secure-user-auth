package io.github.taowang0622.core.code.validation;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Represents a verification code in the format of image
 */
public class ImageCode {
    private BufferedImage image;

    private String code;

    private LocalDateTime expirationTime;

    /**
     *
     * @param image
     * @param code
     * @param timeToLive is in unites of seconds
     */
    public ImageCode(BufferedImage image, String code, int timeToLive) {
        this.image = image;
        this.code = code;
        this.expirationTime = LocalDateTime.now().plusSeconds(timeToLive);
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expirationTime) {
        this.image = image;
        this.code = code;
        this.expirationTime = expirationTime;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expirationTime);
    }
}
