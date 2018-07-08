package io.github.taowang0622.verification.code;

import io.github.taowang0622.core.code.validation.image.ImageCode;
import io.github.taowang0622.core.code.validation.VerificationCodeGenerator;
import io.github.taowang0622.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * By naming this bean as "imageCodeGenerator", it would "override" the default verification code image generator bean(actually that won't be created)
 */
@Component("verificationCodeImageGenerator")
public class DemoVerificationCodeGenerator implements VerificationCodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ImageCode createVerificationCode(ServletWebRequest request) {
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", securityProperties.getCode().getImage().getHeight());
        int length = securityProperties.getCode().getImage().getLength(); //The length of the verification code!!
        char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        int xx = 15;
        int fontHeight = 18;
        int codeY = 16;

        //  Define image buffer
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics gd = buffImg.getGraphics();
        //  Create a random number generator class
        Random random = new Random();
        //  Fill the image with white
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);
        //  Draw Border 。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);
        //  Randomly generated 40 Bar interference line ， The authentication code in the image is not easily detected by other programs 。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 40; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode Used to save randomly generated proof codes ， So that the user logs in to verify it 。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        //  Randomly generated codeCount Digital verification code 。
        for (int i = 0; i < length; i++) {
            //  Get the random number of the validation code 。
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            //  Generates random color components to construct color values ， The color values of each output of this output will vary 。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            //  Draws the validation code into the image with randomly generated colors 。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * xx, codeY);
            //  Combine four random numbers that will be generated 。
            randomCode.append(code);
        }
        return new ImageCode(buffImg, randomCode.toString(), securityProperties.getCode().getImage().getTimeToLive());
    }
}
