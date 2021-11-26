package com.github.captcha4j.core.text.renderer;

import java.awt.image.BufferedImage;

/**
 * Render the answer for the CAPTCHA onto the image.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
@FunctionalInterface
public interface WordRenderer {

    /**
     * Render a word to a BufferedImage.
     *
     * @param word  The sequence of characters to be rendered.
     * @param image The image onto which the word will be rendered.
     */
    void render(String word, BufferedImage image);

}
