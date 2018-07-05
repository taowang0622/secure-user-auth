package io.github.taowang0622.core.code.validation;

import io.github.taowang0622.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class CodeValidationController {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private VerificationCodeGenerator verificationCodeImageGenerator;

    //From spring-social
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //For constants, it should be "static final" where "final" is for sure, and "static" is because it cannot be an instance variable!
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @GetMapping("/code/image")
    public void createCodeImage(HttpServletRequest req, HttpServletResponse res) throws IOException {
        //Generate an image containing a random number
        ImageCode imageCode = verificationCodeImageGenerator.createVerificationCode(new ServletWebRequest(req));
        //Store the randomly generated number to the session for later code validation
        sessionStrategy.setAttribute(new ServletWebRequest(req), SESSION_KEY, imageCode);
        //Stream the image to the response
        ImageIO.write(imageCode.getImage(), "JPEG", res.getOutputStream());
    }

}
