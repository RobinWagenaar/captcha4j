package com.github.captcha4j.core.image.producer;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Produces text of a given length from a given array of characters.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>.
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class DefaultTextProducer implements TextProducer {

    /**
     * The secure random
     */
    private static final Random RAND = new SecureRandom();
    /**
     * The default length
     */
    private static final int DEFAULT_LENGTH = 5;
    /**
     * The default characters
     */
    private static final char[] DEFAULT_CHARS = new char[]{'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 'w', 'x', 'y',
            '2', '3', '4', '5', '6', '7', '8',};

    /**
     * The length
     */
    private final int _length;
    /**
     * The source characters
     */
    private final char[] _srcChars;

    /**
     * Default constructor
     */
    public DefaultTextProducer() {
        this(DEFAULT_LENGTH, DEFAULT_CHARS);
    }

    /**
     * Constructor with customized length and source characters
     *
     * @param length   the length
     * @param srcChars the source characts to chose characters from
     */
    public DefaultTextProducer(int length, char[] srcChars) {
        _length = length;
        _srcChars = copyOf(srcChars, srcChars.length);
    }

    /**
     * Copies content to a new array
     *
     * @param original  the original array
     * @param newLength new length
     * @return the new array
     */
    private static char[] copyOf(char[] original, int newLength) {
        char[] copy = new char[newLength];
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }

    /**
     * Gets the text
     *
     * @return the text string
     */
    @Override
    public String getText() {
        StringBuilder capText = new StringBuilder();
        for (int i = 0; i < _length; i++) {
            capText.append(_srcChars[RAND.nextInt(_srcChars.length)]);
        }

        return capText.toString();
    }
}
