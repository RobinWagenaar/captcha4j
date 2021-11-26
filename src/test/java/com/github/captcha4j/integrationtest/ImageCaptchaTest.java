package com.github.captcha4j.integrationtest;

import com.github.captcha4j.core.text.ImageCaptchaBuilder;
import com.github.captcha4j.core.text.producer.backgrounds.GradientBackgroundProducer;
import com.github.captcha4j.core.text.producer.noise.CurvedLineNoiseProducer;
import com.github.captcha4j.core.text.ImageCaptcha;
import com.github.captcha4j.core.text.gimpy.FishEyeGimpyRenderer;

import javax.swing.*;

public class ImageCaptchaTest {

    //@Test
    public void test() throws InterruptedException {
        ImageCaptcha captcha = new ImageCaptchaBuilder(200, 100)
                .addText()
                .addNoise(new CurvedLineNoiseProducer())
                .addBackground(new GradientBackgroundProducer())
                .gimp(new FishEyeGimpyRenderer())
                .addBorder()
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

        Thread.sleep(10000); //forever
    }
}
