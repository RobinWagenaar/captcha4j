package com.github.captcha4j.core.audio;

import com.github.captcha4j.core.Captcha;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
public class AudioCaptcha implements Captcha {

    String answer;
    Sample audio;

    public AudioCaptcha(String answer, Sample audio){
        this.answer = answer;
        this.audio = audio;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public ByteArrayOutputStream getData() {
        try {
            AudioInputStream ais = getAudio().getAudioInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, os);
            return os;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDataType() {
        return "audio/x-wav";
    }

    public Sample getAudio() {
        return audio;
    }
}
