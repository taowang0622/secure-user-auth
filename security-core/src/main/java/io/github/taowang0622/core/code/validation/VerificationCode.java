package io.github.taowang0622.core.code.validation;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class VerificationCode {
    private String code;

    private LocalDateTime expirationTime;

    public VerificationCode(String code, int timeToLive) {
        this.code = code;
        this.expirationTime = LocalDateTime.now().plusSeconds(timeToLive);
    }

    public VerificationCode(String code, LocalDateTime expirationTime) {
        this.code = code;
        this.expirationTime = expirationTime;
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
