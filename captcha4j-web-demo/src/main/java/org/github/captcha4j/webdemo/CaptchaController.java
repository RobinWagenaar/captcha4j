package org.github.captcha4j.webdemo;

import com.github.captcha4j.core.audio.AudioCaptcha;
import com.github.captcha4j.core.audio.AudioCaptchaBuilder;
import com.github.captcha4j.core.audio.producer.Language;
import com.github.captcha4j.core.image.ImageCaptcha;
import com.github.captcha4j.core.image.ImageCaptchaBuilder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.github.captcha4j.webdemo.CaptchaSolutionEncryptor.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller for generating and validating captcha's.
 *
 * First, a client requests a new challenge by calling the /captcha/challenge endpoint. It
 * generates a random alphanumeric string, and calculates an expiration timestamp based on
 * preset timeout. This endpoint encrypts it, and returns this challenge to the client.
 *
 * The client can choose generate an image or an audio sample based on the challenge:
 *    /captcha/image returns a PNG-image which contains the (somewhat scrambled) text.
 *    /captcha/audio returns a WAV-fragment containing spoken text in a supported language.
 *
 * When solving the captcha, the client send both their answer and the original challenge
 * to the /catpcha/verify endpoint, which decrypts and checks the result.
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    private static final String CAPTCHA_CHALLENGE_HEADER = "X-Captcha-Challenge";
    private static final String CAPTCHA_VALIDITY_HEADER = "X-Captcha-ValidUntil";
    private static final String CAPTCHA_TIMEOUT_HEADER = "X-Captcha-Timeout";

    private final CaptchaSolutionEncryptor captchaService;

    @Value("${captcha.characters}")
    private int captchaLength;

    @Autowired
    public CaptchaController(CaptchaSolutionEncryptor captchaService){
        this.captchaService = captchaService;
    }

    @GetMapping("/challenge")
    public void generateChallenge(HttpServletResponse response) {
        String answer = generateAlphanumericRandomString(captchaLength);
        Challenge challenge = captchaService.encrypt(answer);

        response.addHeader(CAPTCHA_CHALLENGE_HEADER, challenge.getEncryptedData());
        response.addHeader(CAPTCHA_VALIDITY_HEADER, String.valueOf(challenge.getValidUntil()));
        response.addHeader(CAPTCHA_TIMEOUT_HEADER, String.valueOf(challenge.getTimeoutInSeconds()));
    }

    @GetMapping("/image")
    public void generateImage(
            HttpServletResponse response,
            @RequestHeader(value = CAPTCHA_CHALLENGE_HEADER) String encryptedChallenge) throws IOException {
        Challenge challenge = captchaService.decrypt(encryptedChallenge);
        String answer = challenge.getAnswer();

        ImageCaptcha captcha = new ImageCaptchaBuilder(200, 100)
                .addAnswer(()->answer)
                .addNoise()
                .build();

        response.setContentType(MediaType.IMAGE_PNG.toString());
        InputStream is = new ByteArrayInputStream(captcha.getData().toByteArray());
        IOUtils.copy(is, response.getOutputStream());
    }

    @GetMapping("/audio")
    public void generateAudio(
            HttpServletResponse response,
            @RequestHeader(value = CAPTCHA_CHALLENGE_HEADER) String encryptedChallenge,
            @RequestParam(value = "language") Language language) throws IOException {
        Challenge challenge = captchaService.decrypt(encryptedChallenge);
        String answer = challenge.getAnswer();

        AudioCaptcha captcha = new AudioCaptchaBuilder()
                .addAnswer(answer)
                .addVoice(language)
                .build();

        response.setContentType(captcha.getDataType());
        InputStream is = new ByteArrayInputStream(captcha.getData().toByteArray());
        IOUtils.copy(is, response.getOutputStream());
    }


    @PostMapping("/verify")
    @ResponseBody
    public CaptchaVerificationResult verify(
            @RequestHeader(CAPTCHA_CHALLENGE_HEADER) String encryptedChallenge,
            @RequestParam("answer") String userSuppliedAnswer) {

        Challenge challenge = captchaService.decrypt(encryptedChallenge);
        CaptchaVerificationResult result = new CaptchaVerificationResult();

        if (!challenge.isCorrect(userSuppliedAnswer)){
            result.errors.add("ANSWER_INCORRECT");
        }

        if (challenge.isExpired()){
            result.errors.add("CAPTCHA_EXPIRED");
        }

        if (result.errors.isEmpty()){
            result.valid = true;
        }

        return result;
    }

    private String generateAlphanumericRandomString(int length){
        String alphaNumericString = "0123456789abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < sb.capacity(); i++) {
            int index = (int)(alphaNumericString.length()* Math.random());
            sb.append(alphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public static class CaptchaVerificationResult {
        boolean valid = false;
        List<String> errors = new ArrayList<>();
        public boolean isValid() {
            return valid;
        }
        public List<String> getErrors() {
            return errors;
        }
    }
}
