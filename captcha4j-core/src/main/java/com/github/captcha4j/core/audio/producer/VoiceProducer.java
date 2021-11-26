package com.github.captcha4j.core.audio.producer;

import com.github.captcha4j.core.audio.Sample;

/**
 * Generates a vocalization for a single character.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
@FunctionalInterface
public interface VoiceProducer {
    /**
     * Gets vocalization
     *
     * @param letter the letter to convert
     * @return the voice sample
     */
    Sample getVocalization(char letter);
}
