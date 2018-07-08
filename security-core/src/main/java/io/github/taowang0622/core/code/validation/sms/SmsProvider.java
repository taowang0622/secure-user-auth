package io.github.taowang0622.core.code.validation.sms;

public interface SmsProvider {
    void send(String phoneNum, String code);
}
