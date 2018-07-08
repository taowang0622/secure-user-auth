package io.github.taowang0622.core.code.validation.sms;

import io.github.taowang0622.core.code.validation.VerificationCode;
import io.github.taowang0622.core.code.validation.impl.AbastractVerificationCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

@Component
public class SmsCodeProcessor extends AbastractVerificationCodeProcessor<VerificationCode> {
    @Autowired
    private SmsProvider smsProvider;

    @Override
    protected void send(ServletWebRequest request, VerificationCode smsCode) throws IOException, ServletRequestBindingException {
        //There must be a "phone-number" parameter in the request
        String phoneNum = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "phone-number");
        smsProvider.send(phoneNum, smsCode.getCode());
    }
}
