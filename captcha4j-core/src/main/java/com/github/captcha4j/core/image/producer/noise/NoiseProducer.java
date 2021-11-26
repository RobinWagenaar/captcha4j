package com.github.captcha4j.core.image.producer.noise;

import java.awt.image.BufferedImage;

/**
 * Noise producers add noise
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
@FunctionalInterface
public interface NoiseProducer {

    /**
     * Creates and adds the noise
     *
     * @param image the image to add the noise to
     */
    void makeNoise(BufferedImage image);

}
