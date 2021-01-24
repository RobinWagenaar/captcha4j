package com.github.sdtool.statelesscaptcha.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.github.sdtool.statelesscaptcha.core.audio.AudioCaptcha;
import com.github.sdtool.statelesscaptcha.core.text.Captcha;
import com.github.sdtool.statelesscaptcha.exception.CreationException;
import com.github.sdtool.statelesscaptcha.util.Base64Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.sound.sampled.AudioFileFormat;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * The token creator
 *
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Creator {

    /**
     * The token properties
     * Initiated to default
     */
    private TokenProperties properties = new TokenProperties();

    /**
     * Generate token representation of text captcha
     *
     * @param captcha the captcha to convert
     * @return the token model
     */
    public CaptchaToken create(Captcha captcha) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(captcha.getAnswer());
            Date in = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault())
                    .plusSeconds(properties.getValidity());
            Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            String token = JWT.create()
                    .withExpiresAt(out)
                    .withIssuer(properties.getIssuer())
                    .sign(algorithm);
            return new CaptchaToken(
                    Base64Util.encodeImage(captcha.getImage(), "png"),
                    "png",
                    token);
        } catch (JWTCreationException | IllegalArgumentException | IOException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new IllegalArgumentException("Unable to create token");
        }
    }

    /**
     * Generate token representation of audio captcha
     *
     * @param captcha the captcha to convert
     * @return the token model
     */
    public CaptchaToken create(AudioCaptcha captcha) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(captcha.getAnswer());
            Date in = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault())
                    .plusSeconds(properties.getValidity());
            Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            String token = JWT.create()
                    .withExpiresAt(out)
                    .withIssuer(properties.getIssuer())
                    .sign(algorithm);
            return new CaptchaToken(
                    Base64Util.encodeAudio(captcha.getChallenge().getAudioInputStream(),
                            AudioFileFormat.Type.WAVE),
                    "wav",
                    token);
        } catch (JWTCreationException | IllegalArgumentException | IOException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new CreationException("Unable to create token");
        }
    }

}
