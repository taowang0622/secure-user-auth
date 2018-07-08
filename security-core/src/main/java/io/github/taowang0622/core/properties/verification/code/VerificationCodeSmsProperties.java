package io.github.taowang0622.core.properties.verification.code;

public class VerificationCodeSmsProperties {
    private int length = 6;
    private int timeToLive = 60; //in seconds
    private String url;


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
