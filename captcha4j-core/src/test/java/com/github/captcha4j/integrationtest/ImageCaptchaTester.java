package com.github.captcha4j.integrationtest;

import com.github.captcha4j.core.image.ImageCaptchaBuilder;
import com.github.captcha4j.core.image.producer.backgrounds.FlatColorBackgroundProducer;
import com.github.captcha4j.core.image.ImageCaptcha;
import com.github.captcha4j.core.image.renderer.DefaultWordRenderer;
import com.github.captcha4j.core.image.renderer.EmbeddedFont;
import com.github.captcha4j.core.util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class ImageCaptchaTester {

    public static void main(String... args) throws InterruptedException, IOException, FontFormatException {

        EmbeddedFont ef = EmbeddedFont.random();
        Font font = Font.createFont(ef.getType(), FileUtil.readResource(ef.getFilename()));

        ImageCaptcha captcha = new ImageCaptchaBuilder(350, 120)
                .addBackground(new FlatColorBackgroundProducer(Color.ORANGE))
                .addText(()->"1iLIlo0OQDB82Z", new DefaultWordRenderer(Color.RED, font))
                .build();

        JFrame frame = new JFrame("Image captcha test");

        JLabel captchaLabel = new JLabel(new ImageIcon(captcha.getImage()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,200);
        frame.add(captchaLabel);
        frame.pack();

        SwingUtilities.invokeLater(() -> {
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        Thread.sleep(1000); //plenty long time
    }
}
