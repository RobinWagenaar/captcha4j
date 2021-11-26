package com.github.captcha4j.core.image.producer.noise;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

/**
 * Draws a 4-pixel thick straight red line through the given image.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class StraightLineNoiseProducer implements NoiseProducer {

    /**
     * The secure random
     */
    private static final SecureRandom RAND = new SecureRandom();
    /**
     * The color
     */
    private final Color _color;
    /**
     * The thickness
     */
    private final int _thickness;

    /**
     * Default constructor creates a 4-pixel wide red line.
     */
    public StraightLineNoiseProducer() {
        this(Color.RED, 4);
    }

    /**
     * Constructor with customized color and thickness
     *
     * @param color     the color
     * @param thickness thickness
     */
    public StraightLineNoiseProducer(Color color, int thickness) {
        _color = color;
        _thickness = thickness;
    }

    /**
     * Creates and adds the noise
     *
     * @param image the image to add the noise to
     */
    @Override
    public void makeNoise(BufferedImage image) {
        Graphics2D graphics = image.createGraphics();
        int height = image.getHeight();
        int width = image.getWidth();
        int y1 = RAND.nextInt(height) + 1;
        int y2 = RAND.nextInt(height) + 1;
        drawLine(graphics, y1, width, y2);
    }

    /**
     * Draws a line
     *
     * @param g  the graphics
     * @param y1 location y1
     * @param x2 location x2
     * @param y2 location y2
     */
    private void drawLine(Graphics g, int y1, int x2, int y2) {
        int X1 = 0;

        // The thick line is in fact a filled polygon
        g.setColor(_color);
        int dX = x2 - X1;
        int dY = y2 - y1;
        // line length
        double lineLength = Math.sqrt(dX * dX + dY * dY);

        double scale = _thickness / (2 * lineLength);

        // The x and y increments from an endpoint needed to create a
        // rectangle...
        double ddx = -scale * dY;
        double ddy = scale * dX;
        ddx += (ddx > 0) ? 0.5 : -0.5;
        ddy += (ddy > 0) ? 0.5 : -0.5;
        int dx = (int) ddx;
        int dy = (int) ddy;

        // Now we can compute the corner points...
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];

        xPoints[0] = X1 + dx;
        yPoints[0] = y1 + dy;
        xPoints[1] = X1 - dx;
        yPoints[1] = y1 - dy;
        xPoints[2] = x2 - dx;
        yPoints[2] = y2 - dy;
        xPoints[3] = x2 + dx;
        yPoints[3] = y2 + dy;

        g.fillPolygon(xPoints, yPoints, 4);
    }

}