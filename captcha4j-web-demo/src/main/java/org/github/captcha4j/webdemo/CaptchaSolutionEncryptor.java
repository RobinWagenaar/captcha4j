package org.github.captcha4j.webdemo;

import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CaptchaSolutionEncryptor {

    @Value("${captcha.timeout}")
    private int captchaTimeout; //seconds

    private final Pattern captchaSolutionRegex = Pattern.compile("^(?<validUntil>[0-9]{13})\\.(?<solution>.{1,20})$");
    private final TextEncryptor textEncryptor;

    @Autowired
    public CaptchaSolutionEncryptor(TextEncryptor textEncryptor){
        this.textEncryptor = textEncryptor;
    }

    public Challenge encrypt(String answer){
        long validUntil = System.currentTimeMillis() + (captchaTimeout * 1000);
        String encryptedData = textEncryptor.encrypt(validUntil + "." + answer);
        return new Challenge(
            answer,
            validUntil,
            captchaTimeout,
            encryptedData
        );
    }

    public Challenge decrypt(String encryptedData){
        Matcher m = captchaSolutionRegex.matcher(textEncryptor.decrypt(encryptedData));
        if(!m.matches()) {
            throw new IllegalArgumentException("Unexpected content in encrypted string");
        }

        return new Challenge(
            m.group("solution"),
            Long.parseLong(m.group("validUntil")),
            captchaTimeout,
            encryptedData
        );
    }

    public static class Challenge {
        private final String answer;
        private final String encryptedData;
        private final long validUntil;
        private final int timeoutInSeconds;

        public Challenge(String answer, long validUntil, int timeoutInSeconds, String encryptedData) {
            this.answer = answer.trim();
            this.validUntil = validUntil;
            this.timeoutInSeconds = timeoutInSeconds;
            this.encryptedData = encryptedData;
        }

        public boolean isExpired(){
            return System.currentTimeMillis() > validUntil;
        }

        public boolean isCorrect(String userSuppliedAnswer){
            return getAnswer().equals(userSuppliedAnswer.trim());
        }

        public String getAnswer() {
            return answer;
        }

        public String getEncryptedData() {
            return encryptedData;
        }

        public long getValidUntil() {
            return validUntil;
        }

        public int getTimeoutInSeconds() {
            return timeoutInSeconds;
        }
    }

}
