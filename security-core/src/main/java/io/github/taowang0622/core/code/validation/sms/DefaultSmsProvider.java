package io.github.taowang0622.core.code.validation.sms;

public class DefaultSmsProvider implements SmsProvider {
    @Override
    public void send(String phoneNum, String code) {
        System.out.println("Send code "+ code + " to " + phoneNum);
    }
}
