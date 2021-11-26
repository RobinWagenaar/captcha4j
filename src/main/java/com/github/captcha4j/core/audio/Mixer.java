package com.github.captcha4j.core.audio;

import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;



/**
 * Helper class for operating on audio {@link Sample}s.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class Mixer {

    /**
     * Appends the samples together
     *
     * @param samples the list of samples
     * @return the combined sample
     */
    public static Sample append(List<Sample> samples) {
        if (samples.size() == 0) {
            return buildSample(0, new double[0]);
        }

        int sampleCount = 0;

        // append voices to each other
        double[] first = samples.get(0).getInterleavedSamples();
        sampleCount += samples.get(0).getSampleCount();

        double[][] samples_ary = new double[samples.size() - 1][];
        for (int i = 0; i < samples_ary.length; i++) {
            samples_ary[i] = samples.get(i + 1).getInterleavedSamples();
            sampleCount += samples.get(i + 1).getSampleCount();
        }

        double[] appended = concatAll(first, samples_ary);

        return buildSample(sampleCount, appended);
    }

    /**
     * Mixes two audio samples
     *
     * @param sample1 the first sample
     * @param volAdj1 the first sample volume
     * @param sample2 the second sample
     * @param volAdj2 the second sample volume
     * @return the combined sample
     */
    public static Sample mix(Sample sample1, double volAdj1,
                             Sample sample2, double volAdj2) {
        double[] s1_ary = sample1.getInterleavedSamples();
        double[] s2_ary = sample2.getInterleavedSamples();

        //
        double[] mixed = mix(s1_ary, volAdj1, s2_ary, volAdj2);

        return buildSample(sample1.getSampleCount(), mixed);
    }

    /**
     * Concatenates all double array
     *
     * @param first the first array
     * @param rest  the rest of the arrays
     * @return the combined array
     */
    private static double[] concatAll(double[] first, double[]... rest) {
        int totalLength = first.length;
        for (double[] array : rest) {
            totalLength += array.length;
        }
        double[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (double[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Mixes two array samples
     *
     * @param sample1 the first array
     * @param volAdj1 the first array volume
     * @param sample2 the second array
     * @param volAdj2 the second array volume
     * @return the combined sample
     */
    private static double[] mix(double[] sample1, double volAdj1, double[] sample2,
                                double volAdj2) {
        for (int i = 0; i < sample1.length; i++) {
            if (i >= sample2.length) {
                sample1[i] = 0;
                break;
            }
            sample1[i] = (sample1[i] * volAdj1) + (sample2[i] * volAdj2);
        }
        return sample1;
    }

    /**
     * Generates the audio stream
     *
     * @param sampleCount the sample count
     * @param sample      the samples as array
     * @return the audio stream
     */
    private static AudioInputStream buildStream(long sampleCount,
                                                double[] sample) {
        byte[] buffer = Sample.asByteArray(sampleCount, sample);
        InputStream bais = new ByteArrayInputStream(buffer);
        return new AudioInputStream(bais, Sample.SC_AUDIO_FORMAT, sampleCount);
    }

    /**
     * Builds audio sample from array sample
     *
     * @param sampleCount the sample count
     * @param sample      the samples as array
     * @return the audio sample
     */
    private static Sample buildSample(long sampleCount, double[] sample) {
        AudioInputStream ais = buildStream(sampleCount, sample);
        return new Sample(ais);
    }

}
