package com.github.captcha4j.core.audio;

import com.github.captcha4j.core.audio.producer.Language;
import com.github.captcha4j.core.audio.producer.NumberVoiceProducer;
import com.github.captcha4j.core.audio.producer.VoiceProducer;
import com.github.captcha4j.core.audio.producer.noise.NoiseProducer;
import com.github.captcha4j.core.audio.producer.noise.RandomNoiseProducer;
import com.github.captcha4j.core.image.producer.NumbersAnswerProducer;
import com.github.captcha4j.core.image.producer.TextProducer;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The captcha builder class
 *
 * @author <a href="mailto:robin.wagenaar@gmail.com">Robin Wagenaar</a>
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 */
public class AudioCaptchaBuilder {

    private static final Random RAND = new SecureRandom();


    private String answer = "";
    private Sample challenge;

    private List<VoiceProducer> voiceProds;
    private List<NoiseProducer> noiseProds;

    public AudioCaptchaBuilder() {
        voiceProds = new ArrayList<>();
        noiseProds = new ArrayList<>();
    }

    /**
     * Add the answer with default answer producer
     *
     * @return the builder with answer added
     */
    public AudioCaptchaBuilder addRandomAnswer() {
        return addAnswer(new NumbersAnswerProducer());
    }

    /**
     * Add the answer with provided answer producer
     *
     * @param ansProd the answer producer
     * @return the builder with answer added
     */
    public AudioCaptchaBuilder addAnswer(TextProducer ansProd) {
        answer += ansProd.getText();

        return this;
    }

    /**
     * Add the voice with default voice producer
     *
     * @return the builder with voice added
     */
    public AudioCaptchaBuilder addVoice(Language lang) {
        voiceProds.add(new NumberVoiceProducer(lang));

        return this;
    }

    /**
     * Add the voice with provided voice producer
     *
     * @param vProd the voice producer
     * @return the builder with voice added
     */
    public AudioCaptchaBuilder addVoice(VoiceProducer vProd) {
        voiceProds.add(vProd);

        return this;
    }

    /**
     * Adds noise to captcha with default noise producer
     *
     * @return the builder with noise added
     */
    public AudioCaptchaBuilder addNoise() {
        return addNoise(new RandomNoiseProducer());
    }

    /**
     * Adds noise to captcha with customized noise producer
     *
     * @param noiseProd the noise producer
     * @return the builder with noise added
     */
    public AudioCaptchaBuilder addNoise(NoiseProducer noiseProd) {
        noiseProds.add(noiseProd);
        return this;
    }



    /**
     * Builds the captcha
     *
     * @return the audio captcha
     */
    public AudioCaptcha build() {
        // Make sure we have at least one voiceProducer
        if (voiceProds.size() == 0) {
            addVoice(Language.EN); //default EN
        }

        // Convert answer to an array
        char[] ansAry = answer.toCharArray();

        // Make a List of Samples for each character
        VoiceProducer vProd;
        List<Sample> samples = new ArrayList<>();
        Sample sample;
        for (char c : ansAry) {
            // Create Sample for this character from one of the
            // VoiceProducers
            vProd = voiceProds.get(RAND.nextInt(voiceProds.size()));
            sample = vProd.getVocalization(c);
            samples.add(sample);
        }

        // 3. Add noise, if any, and return the result
        if (noiseProds.size() > 0) {
            NoiseProducer nProd = noiseProds.get(RAND.nextInt(noiseProds
                    .size()));
            challenge = nProd.addNoise(samples);

            return new AudioCaptcha(answer, challenge);
        }

        challenge = Mixer.append(samples);

        return new AudioCaptcha(answer, challenge);
    }

}