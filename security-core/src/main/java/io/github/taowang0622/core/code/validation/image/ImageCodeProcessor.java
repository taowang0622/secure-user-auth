package io.github.taowang0622.core.code.validation.image;

import io.github.taowang0622.core.code.validation.impl.AbastractVerificationCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

@Component
public class ImageCodeProcessor extends AbastractVerificationCodeProcessor<ImageCode>{

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws IOException {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
