package io.github.taowang0622.core.code.validation.image;

import io.github.taowang0622.core.code.validation.VerificationCodeGenerator;
import io.github.taowang0622.core.properties.SecurityProperties;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerificationCodeImageGenerator implements VerificationCodeGenerator {
//    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ImageCode createVerificationCode(ServletWebRequest request) {
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", securityProperties.getCode().getImage().getHeight());
        int length = securityProperties.getCode().getImage().getLength(); //The length of the verification code!!

        // 图片验证码对象
//        ImageVerifyItem item = new ImageVerifyItem();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics graphics = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        graphics.setColor(getRandColor(200, 250));
        // 填充指定的矩形
        graphics.fillRect(0, 0, width, height);
        // 设定字体
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        // 画边框
        // graphics.setColor(new Color());
        // graphics.drawRect(0, 0, width-1, height-1);
        // 随机产生155条干扰线，使图像中的认证码不易被其他程序探测到
        graphics.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, xl, yl);
        }
        // 取随机产生的验证码
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            // 将验证码显示到图像中
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            graphics.drawString(rand, 13*i +6, 16);
        }
        // 释放此图形的上下文以及它使用的所有系统资源。
        graphics.dispose();

        return new ImageCode(image, sRand.toString(), securityProperties.getCode().getImage().getTimeToLive());
    }

    /**
     * Return a random color according to specified range!!!
     * */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
