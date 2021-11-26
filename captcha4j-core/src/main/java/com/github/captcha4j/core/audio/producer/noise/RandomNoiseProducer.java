package com.github.captcha4j.core.audio.producer.noise;

import com.github.captcha4j.core.audio.Mixer;
import com.github.captcha4j.core.audio.Sample;
import com.github.captcha4j.core.util.FileUtil;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Adds noise to a {@link Sample} from one of the given <code>noiseFiles</code>.
 * By default this noise comes from one of three files, all located in
 * <code>/sounds/noises/</code>: <code>radio_tuning.wav</code>,
 * <code>restaurant.wav</code>, and <code>swimming.wav</code>. This can be
 * overridden by passing the location of your own sound files to the
 * constructor, e.g.:
 *
 * <pre>
 * String myFiles = { &quot;/mysounds/noise1.wav&quot;, &quot;/mysounds/noise2.wav&quot; };
 * NoiseProducer myNp = new RandomNoiseProducer(myFiles);
 * </pre>
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 */
public class RandomNoiseProducer implements NoiseProducer {

    /**
     * The secure random
     */
    private static final Random RAND = new SecureRandom();
    /**
     * The default noises
     */
    private static final String[] DEFAULT_NOISES = {
            "/sounds/noises/radio_tuning.wav",
            "/sounds/noises/restaurant.wav",
            "/sounds/noises/swimming.wav",
            "/sounds/noises/zombie.wav"};

    /**
     * The noise files
     */
    private final String[] _noiseFiles;

    /**
     * The default constructor
     */
    public RandomNoiseProducer() {
        this(DEFAULT_NOISES);
    }

    /**
     * The constructor with customized noise files
     *
     * @param noiseFiles the noise files to use
     */
    public RandomNoiseProducer(String[] noiseFiles) {
        _noiseFiles = noiseFiles;
    }

    /**
     * Append the given <code>samples</code> to each other, then add random
     * noise to the result.
     */
    @Override
    public Sample addNoise(List<Sample> samples) {
        Sample appended = Mixer.append(samples);
        String noiseFile = _noiseFiles[RAND.nextInt(_noiseFiles.length)];
        Sample noise = FileUtil.readSample(noiseFile);

        // Decrease the volume of the noise to make sure the voices can be heard
        return Mixer.mix(appended, 1.0, noise, 0.6);
    }

    /**
     * To String
     *
     * @return the string representing the producer
     */
    @Override
    public String toString() {
        return "[Noise files: " +
                Arrays.toString(_noiseFiles) +
                "]";
    }
}
