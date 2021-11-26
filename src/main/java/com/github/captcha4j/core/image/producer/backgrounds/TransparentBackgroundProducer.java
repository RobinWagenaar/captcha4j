package com.github.captcha4j.core.image.producer.backgrounds;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Generates a transparent background.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class TransparentBackgroundProducer implements BackgroundProducer {

    /**
     * Add the background to the given image.
     *
     * @param image The image onto which the background will be rendered.
     * @return The image with the background rendered.
     */
    @Override
    public BufferedImage addBackground(BufferedImage image) {
        return getBackground(image.getWidth(), image.getHeight());
    }

    /**
     * Gets the background
     *
     * @param width the width
     * @param height the height
     * @return The image with the background rendered.
     */
    @Override
    public BufferedImage getBackground(int width, int height) {
        BufferedImage bg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g = bg.createGraphics();

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
        g.fillRect(0, 0, width, height);

        return bg;
    }

}
