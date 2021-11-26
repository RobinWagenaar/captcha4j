package com.github.captcha4j.core.image.producer.backgrounds;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Flat color background producer
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public final class FlatColorBackgroundProducer implements BackgroundProducer {

    /**
     * The color
     */
    private Color _color;

    /**
     * Default constructor
     */
    public FlatColorBackgroundProducer() {
        this(Color.GRAY);
    }

    /**
     * Constructor with customized color
     *
     * @param color the background color
     */
    public FlatColorBackgroundProducer(Color color) {
        _color = color;
    }

    /**
     * Add the background to the given image.
     *
     * @param image The image onto which the background will be rendered.
     * @return The image with the background rendered.
     */
    @Override
    public BufferedImage addBackground(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        return this.getBackground(width, height);
    }

    /**
     * Gets the background
     *
     * @param width  the width
     * @param height the height
     * @return The image with the background rendered.
     */
    @Override
    public BufferedImage getBackground(int width, int height) {
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = img.createGraphics();
        graphics.setPaint(_color);
        graphics.fill(new Rectangle2D.Double(0, 0, width, height));
        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();

        return img;
    }

}
