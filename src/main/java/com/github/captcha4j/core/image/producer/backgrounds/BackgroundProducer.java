package com.github.captcha4j.core.image.producer.backgrounds;

import java.awt.image.BufferedImage;

/**
 * Background producers to add background
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public interface BackgroundProducer {

    /**
     * Add the background to the given image.
     *
     * @param image The image onto which the background will be rendered.
     * @return The image with the background rendered.
     */
    BufferedImage addBackground(BufferedImage image);

    /**
     * Gets the background
     *
     * @param width  the width
     * @param height the height
     * @return The image with the background rendered.
     */
    BufferedImage getBackground(int width, int height);

}
