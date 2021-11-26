package com.github.captcha4j.core.image.gimpy;

import java.awt.image.BufferedImage;

/**
 * Gimpy render for adding distortion to image
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
@FunctionalInterface
public interface GimpyRenderer {

    /**
     * Applies the rendering to the image
     *
     * @param image the image
     */
    void gimp(BufferedImage image);

}
