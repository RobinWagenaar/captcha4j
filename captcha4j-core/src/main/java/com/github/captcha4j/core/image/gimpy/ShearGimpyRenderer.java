package com.github.captcha4j.core.image.gimpy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Shears the image
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class ShearGimpyRenderer implements GimpyRenderer {

    /**
     * The secure random
     */
    private static final Random RAND = new SecureRandom();
    /**
     * The color
     */
    private final Color _color;

    /**
     * Default constructor
     */
    public ShearGimpyRenderer() {
        this(Color.GRAY);
    }

    /**
     * Constructor with customized color
     *
     * @param color the color
     */
    public ShearGimpyRenderer(Color color) {
        _color = color;
    }

    /**
     * Applies the rendering to the image
     *
     * @param image the image
     */
    @Override
    public void gimp(BufferedImage image) {
        Graphics2D g = image.createGraphics();
        shearX(g, image.getWidth(), image.getHeight());
        shearY(g, image.getWidth(), image.getHeight());
        g.dispose();
    }

    /**
     * Shears in x axis
     *
     * @param g the 2d graphics
     * @param w the width
     * @param h the height
     */
    private void shearX(Graphics2D g, int w, int h) {
        int period = RAND.nextInt(10) + 5;

        int frames = 15;
        int phase = RAND.nextInt(5) + 2;

        for (int i = 0; i < h; i++) {
            double d = (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * phase) / frames);
            g.copyArea(0, i, w, 1, (int) d, 0);
            g.setColor(_color);
            g.drawLine((int) d, i, 0, i);
            g.drawLine((int) d + w, i, w, i);
        }
    }

    /**
     * Shears in y axis
     *
     * @param g the 2d graphics
     * @param w the width
     * @param h the height
     */
    private void shearY(Graphics2D g, int w, int h) {
        int period = RAND.nextInt(30) + 10; // 50;

        int frames = 15;
        int phase = 7;
        for (int i = 0; i < w; i++) {
            double d = (period >> 1)
                    * Math.sin((float) i / period
                    + (6.2831853071795862D * phase) / frames);
            g.copyArea(i, 0, 1, h, 0, (int) d);
            g.setColor(_color);
            g.drawLine(i, (int) d, i, 0);
            g.drawLine(i, (int) d + h, i, h);
        }
    }

}
