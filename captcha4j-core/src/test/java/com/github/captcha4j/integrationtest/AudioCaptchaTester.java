package com.github.captcha4j.integrationtest;

import com.github.captcha4j.core.audio.AudioCaptcha;
import com.github.captcha4j.core.audio.AudioCaptchaBuilder;
import com.github.captcha4j.core.audio.producer.Language;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioCaptchaTester {

    public static void main(String... args) throws InterruptedException, LineUnavailableException, IOException {
        AudioCaptcha audioCaptcha = new AudioCaptchaBuilder()
                .addAnswer("1a23d45c")
                .addVoice(Language.NL)
                .addNoise()
                .build();

        AudioInputStream stream = audioCaptcha.getAudio().getAudioInputStream();
        AudioFormat format = audioCaptcha.getAudio().getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(stream);
        clip.start();

        do {
            Thread.sleep(10);
        }while(clip.isRunning());
    }
}
