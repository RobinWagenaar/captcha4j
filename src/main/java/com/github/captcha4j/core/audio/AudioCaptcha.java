package com.github.captcha4j.core.audio;

import com.github.captcha4j.core.audio.producer.RandomNumberVoiceProducer;
import com.github.captcha4j.core.audio.producer.VoiceProducer;
import com.github.captcha4j.core.audio.producer.noise.NoiseProducer;
import com.github.captcha4j.core.audio.producer.noise.RandomNoiseProducer;
import com.github.captcha4j.core.text.producer.NumbersAnswerProducer;
import com.github.captcha4j.core.text.producer.TextProducer;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A builder for generating a CAPTCHA audio/answer pair.
 *
 * <p>
 * Example for generating a new CAPTCHA:
 * </p>
 *
 * <pre>
 * AudioCaptcha ac = new AudioCaptcha.Builder()
 *   .addAnswer()
 *   .addNoise()
 *   .build();
 * </pre>
 * <p>
 * Note that the <code>build()</code> method must always be called last. Other
 * methods are optional.
 * </p>
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public final class AudioCaptcha {

    /**
     * The secure random
     */
    private static final Random RAND = new SecureRandom();

    /**
     * The builder of the captcha
     */
    private Builder _builder;

    /**
     * The constructor with customized builder
     *
     * @param builder the captcha builder
     */
    private AudioCaptcha(Builder builder) {
        _builder = builder;
    }

    /**
     * Verifies captcha
     *
     * @param answer the answer to verify
     * @return if the answer is correct
     */
    public boolean isCorrect(String answer) {
        return answer.equals(_builder._answer);
    }

    /**
     * Gets the answer
     *
     * @return the answer
     */
    public String getAnswer() {
        return _builder._answer;
    }

    /**
     * Gets the challenge
     *
     * @return the challenge
     */
    public Sample getChallenge() {
        return _builder._challenge;
    }

    /**
     * To String
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return _builder.toString();
    }

    /**
     * The captcha builder class
     */
    public static class Builder {

        /**
         * The answer
         */
        private String _answer = "";
        /**
         * The challenge
         */
        private Sample _challenge;
        /**
         * List of voice producers
         */
        private List<VoiceProducer> _voiceProds;
        /**
         * List of noise producers
         */
        private List<NoiseProducer> _noiseProds;

        /**
         * Default constructor
         */
        public Builder() {
            _voiceProds = new ArrayList<>();
            _noiseProds = new ArrayList<>();
        }

        /**
         * Add the answer with default answer producer
         *
         * @return the builder with answer added
         */
        public Builder addAnswer() {
            return addAnswer(new NumbersAnswerProducer());
        }

        /**
         * Add the answer with provided answer producer
         *
         * @param ansProd the answer producer
         * @return the builder with answer added
         */
        public Builder addAnswer(TextProducer ansProd) {
            _answer += ansProd.getText();

            return this;
        }

        /**
         * Add the voice with default voice producer
         *
         * @return the builder with voice added
         */
        public Builder addVoice() {
            _voiceProds.add(new RandomNumberVoiceProducer());

            return this;
        }

        /**
         * Add the voice with provided voice producer
         *
         * @param vProd the voice producer
         * @return the builder with voice added
         */
        public Builder addVoice(VoiceProducer vProd) {
            _voiceProds.add(vProd);

            return this;
        }

        /**
         * Adds noise to captcha with default noise producer
         *
         * @return the builder with noise added
         */
        public Builder addNoise() {
            return addNoise(new RandomNoiseProducer());
        }

        /**
         * Adds noise to captcha with customized noise producer
         *
         * @param noiseProd the noise producer
         * @return the builder with noise added
         */
        public Builder addNoise(NoiseProducer noiseProd) {
            _noiseProds.add(noiseProd);
            return this;
        }

        /**
         * Builds the captcha
         *
         * @return the audio captcha
         */
        public AudioCaptcha build() {
            // Make sure we have at least one voiceProducer
            if (_voiceProds.size() == 0) {
                addVoice();
            }

            // Convert answer to an array
            char[] ansAry = _answer.toCharArray();

            // Make a List of Samples for each character
            VoiceProducer vProd;
            List<Sample> samples = new ArrayList<>();
            Sample sample;
            for (char c : ansAry) {
                // Create Sample for this character from one of the
                // VoiceProducers
                vProd = _voiceProds.get(RAND.nextInt(_voiceProds.size()));
                sample = vProd.getVocalization(c);
                samples.add(sample);
            }

            // 3. Add noise, if any, and return the result
            if (_noiseProds.size() > 0) {
                NoiseProducer nProd = _noiseProds.get(RAND.nextInt(_noiseProds
                        .size()));
                _challenge = nProd.addNoise(samples);

                return new AudioCaptcha(this);
            }

            _challenge = Mixer.append(samples);

            return new AudioCaptcha(this);
        }

        /**
         * To String
         *
         * @return the string representation of the captcha
         */
        @Override
        public String toString() {
            return "[Answer: " +
                    _answer +
                    "]";
        }
    }
}
