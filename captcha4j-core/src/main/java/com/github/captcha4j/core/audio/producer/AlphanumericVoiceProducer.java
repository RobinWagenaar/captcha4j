package com.github.captcha4j.core.audio.producer;

import com.github.captcha4j.core.audio.Sample;
import com.github.captcha4j.core.util.FileUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;

/**
 * <p>
 * {@link VoiceProducer} which generates a vocalization for a given number,
 * randomly selecting from a list of voices available for a given language.
 *
 * @author <a href="mailto:wagenaar.robin@gmail.com">Robin Wagenaar</a>
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class AlphanumericVoiceProducer implements VoiceProducer {
    private Language currentLanguage;
    private static final Random RAND = new SecureRandom();

    private static Map<Language, List<String>> VOICES_BY_LANGUAGE = new HashMap<Language, List<String>>(){{
        put(Language.EN, Arrays.asList(
                "acclivity",
                "desuperanton"
        ));

        put(Language.NL, Arrays.asList(
                "robin",
                "vincent",
                "nynke",
                "liesbeth"
        ));
    }};

    public AlphanumericVoiceProducer(Language language){
        this.currentLanguage = language;
    }

    /**
     * Gets the vocalization
     *
     * @param chr the character to vocalize
     * @oaram lang the language needed
     * @return the vocal/audio sample of the character
     */
    @Override
    public final Sample getVocalization(char chr) {
        if (!String.valueOf(chr).matches("^[a-zA-Z0-9]*$")){
            throw new IllegalArgumentException("Voice samples are only available for a-z and 0-9. Requested was: " + chr);
        }

        List<String> voices = VOICES_BY_LANGUAGE.get(currentLanguage);
        String randomVoice = voices.get(RAND.nextInt(voices.size()));
        String file = "/sounds/voices/"+currentLanguage.toString().toLowerCase()+"/"+randomVoice+"/"+Character.toLowerCase(chr)+"-"+randomVoice + ".wav";
        System.out.println(file);
        return FileUtil.readSample(file);
    }

}
