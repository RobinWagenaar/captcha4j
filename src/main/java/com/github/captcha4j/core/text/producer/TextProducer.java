package com.github.captcha4j.core.text.producer;


/**
 * Generate an answer for the CAPTCHA.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
@FunctionalInterface
public interface TextProducer {

    /**
     * Generate a series of characters to be used as the answer for the CAPTCHA.
     *
     * @return The answer for the CAPTCHA.
     */
    String getText();
}
