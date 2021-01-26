package com.github.sdtool.statelesscaptcha.core.audio.producer.noise;

import com.github.sdtool.statelesscaptcha.core.audio.Sample;

import java.util.List;

/**
 * Noise producer
 *
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
@FunctionalInterface
public interface NoiseProducer {
    /**
     * Adds noise
     *
     * @param target the target
     * @return the sample
     */
    Sample addNoise(List<Sample> target);
}
