package io.github.taowang0622.core.code.validation;

import io.github.taowang0622.core.code.validation.sms.SmsProvider;
import io.github.taowang0622.core.code.validation.image.ImageCode;
import io.github.taowang0622.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class CodeValidationController {
    @Autowired
    private Map<String, VerificationCodeProcessor> verificationCodeProcessors;

    /**
     * Create a verification code, store it into the session and then return it to the client. According to the passed type, create
     * different types of code.
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        verificationCodeProcessors.get(type + "CodeProcessor").create(new ServletWebRequest(request, response));
    }


//    @Autowired
//    private SecurityProperties securityProperties;
//
//    @Autowired
//    private VerificationCodeGenerator imageCodeGenerator;
//    @Autowired
//    private VerificationCodeGenerator verificationCodeSmsGenerator;
//
//    @Autowired
//    private SmsProvider smsProvider;
//
//    //From spring-social
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
//
//    //For constants, it should be "static final" where "final" is for sure, and "static" is because it cannot be an instance variable!
//    public static final String SESSION_KEY = "SESSION_KEY_VERIFICATION_CODE";
//
//    @GetMapping("/code/image")
//    public void createCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        //Generate an image containing a random number
//        ImageCode imageCode = (ImageCode) imageCodeGenerator.createVerificationCode(new ServletWebRequest(request));
//        //Store the randomly generated number to the session for later code validation
//        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
//        //Stream the image to the response
//        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
//    }
//
//    @GetMapping("/code/sms")
//    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
//        VerificationCode smsCode = verificationCodeSmsGenerator.createVerificationCode(new ServletWebRequest(request));
//        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
//        //There must be a "phone-number" parameter in the request
//        String phoneNum = ServletRequestUtils.getRequiredStringParameter(request, "phone-number");
//        smsProvider.send(phoneNum, smsCode.getCode());
//    }

}
