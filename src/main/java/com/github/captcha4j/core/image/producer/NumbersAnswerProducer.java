package com.github.captcha4j.core.image.producer;


/**
 * TextProducer implementation that will return a series of numbers.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class NumbersAnswerProducer implements TextProducer {

    /**
     * The default length
     */
    private static final int DEFAULT_LENGTH = 5;
    /**
     * The numbers character
     */
    private static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9'};

    /**
     * The text producer
     */
    private final TextProducer _txtProd;

    /**
     * Constructor with default length
     */
    public NumbersAnswerProducer() {
        this(DEFAULT_LENGTH);
    }

    /**
     * Constructor with custom length
     *
     * @param length the length
     */
    public NumbersAnswerProducer(int length) {
        _txtProd = new DefaultTextProducer(length, NUMBERS);
    }

    /**
     * Gets the text
     *
     * @return the text
     */
    @Override
    public String getText() {
        return _txtProd.getText();
    }

}
