package org.github.captcha4j.webdemo;

import com.github.captcha4j.core.audio.AudioCaptcha;
import com.github.captcha4j.core.audio.AudioCaptchaBuilder;
import com.github.captcha4j.core.audio.producer.Language;
import com.github.captcha4j.core.image.ImageCaptcha;
import com.github.captcha4j.core.image.ImageCaptchaBuilder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    private static final String CAPTCHA_SOLUTION_HEADER = "X-Captcha-Solution";
    private static final String CAPTCHA_VALIDITY_HEADER = "X-Captcha-ValidUntil";
    private static final String CAPTCHA_TIMEOUT_HEADER = "X-Captcha-Timeout";

    private final CaptchaSolutionEncryptor captchaService;

    @Autowired
    public CaptchaController(CaptchaSolutionEncryptor captchaService){
        this.captchaService = captchaService;
    }


    @GetMapping("/image")
    public void generateImage(HttpServletResponse response) throws IOException {
        String answer = "asdfasf123";
        ImageCaptcha captcha = new ImageCaptchaBuilder(200, 100)
                .addAnswer(()->answer)
                .addNoise()
                .build();

        response.setContentType(MediaType.IMAGE_PNG.toString());
        addCaptchaHeaders(response, answer);

        InputStream is = new ByteArrayInputStream(captcha.getData().toByteArray());
        IOUtils.copy(is, response.getOutputStream());
    }

    @GetMapping("/audio")
    public void generateAudio(HttpServletResponse response) throws IOException {
        String answer = "123456789";
        AudioCaptcha captcha = new AudioCaptchaBuilder()
                .addAnswer(answer)
                .addVoice(Language.EN)
                .build();

        response.setContentType(captcha.getDataType());
        addCaptchaHeaders(response, answer);

        InputStream is = new ByteArrayInputStream(captcha.getData().toByteArray());
        IOUtils.copy(is, response.getOutputStream());
    }


    @PostMapping("/verify")
    @ResponseBody
    public CaptchaVerificationResult verify(
            @RequestHeader(CAPTCHA_SOLUTION_HEADER) String encryptedSolution,
            @RequestParam("answer") String userSuppliedAnswer) {

        CaptchaSolutionEncryptor.Solution solution = captchaService.decrypt(encryptedSolution);
        CaptchaVerificationResult result = new CaptchaVerificationResult();

        if (!solution.isCorrect(userSuppliedAnswer)){
            result.errors.add("ANSWER_INCORRECT");
        }

        if (solution.isExpired()){
            result.errors.add("CAPTCHA_EXPIRED");
        }

        if (result.errors.isEmpty()){
            result.valid = true;
        }

        return result;
    }

    private void addCaptchaHeaders(HttpServletResponse response, String answer){
        CaptchaSolutionEncryptor.Solution solution = captchaService.encrypt(answer);
        response.addHeader(CAPTCHA_SOLUTION_HEADER, solution.getEncryptedData());
        response.addHeader(CAPTCHA_VALIDITY_HEADER, String.valueOf(solution.getValidUntil()));
        response.addHeader(CAPTCHA_TIMEOUT_HEADER, String.valueOf(solution.getTimeoutInSeconds()));
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
