package com.github.captcha4j.integrationtest;

import com.github.captcha4j.core.image.ImageCaptchaBuilder;
import com.github.captcha4j.core.image.gimpy.BlockGimpyRenderer;
import com.github.captcha4j.core.image.gimpy.RippleGimpyRenderer;
import com.github.captcha4j.core.image.producer.backgrounds.FlatColorBackgroundProducer;
import com.github.captcha4j.core.image.producer.backgrounds.GradientBackgroundProducer;
import com.github.captcha4j.core.image.producer.backgrounds.SquigglesBackgroundProducer;
import com.github.captcha4j.core.image.producer.backgrounds.TransparentBackgroundProducer;
import com.github.captcha4j.core.image.producer.noise.CurvedLineNoiseProducer;
import com.github.captcha4j.core.image.ImageCaptcha;
import com.github.captcha4j.core.image.gimpy.FishEyeGimpyRenderer;
import com.github.captcha4j.core.image.renderer.ColoredEdgesWordRenderer;
import com.github.captcha4j.core.image.renderer.DefaultWordRenderer;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ImageCaptchaTester {

    public static void main(String... args) throws InterruptedException {
        ImageCaptcha captcha = new ImageCaptchaBuilder(300, 150)
                .addBackground(new FlatColorBackgroundProducer(Color.ORANGE))
                .addText(()->"NiceRipple",
                        new DefaultWordRenderer(Arrays.asList(Color.RED), Arrays.asList(new Font("cooper black", Font.PLAIN, 50)))
                )
                .gimp(new FishEyeGimpyRenderer())
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
