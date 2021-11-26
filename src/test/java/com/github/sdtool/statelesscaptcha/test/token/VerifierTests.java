/*
 * Copyright 2021 Subhajit Das
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.sdtool.statelesscaptcha.test.token;

import com.github.sdtool.statelesscaptcha.core.audio.AudioCaptcha;
import com.github.sdtool.statelesscaptcha.core.text.Captcha;
import com.github.sdtool.statelesscaptcha.exception.VerificationException;
import com.github.sdtool.statelesscaptcha.token.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifierTests {

    @Test
    public void testAudioTokenValidation() {
        String ANSWER = "12345";
        AudioCaptcha captcha = new AudioCaptcha.Builder()
                .addAnswer(() -> ANSWER)
                .addNoise()
                .addVoice()
                .build();

        String jwt = new Creator().createSignedJWT(ANSWER);
        new Verifier().verify(jwt, ANSWER);
    }

    @Test
    public void testAudioTokenValidationFail() {
        String ANSWER = "12345";
        AudioCaptcha captcha = new AudioCaptcha.Builder()
                .addAnswer(() -> ANSWER)
                .addNoise()
                .addVoice()
                .build();
        String token = new Creator().createSignedJWT(ANSWER);
        Assertions.assertThrows(VerificationException.class,
                () -> new Verifier().verify(token, ANSWER + "2"));
    }

    @Test
    public void testTokenExpired() throws InterruptedException {
        TokenProperties props = new TokenProperties();
        props.setValidity(0);

        Captcha captcha = new Captcha.Builder(200, 50)
                .addText()
                .addNoise()
                .addBackground()
                .build();

        Creator creator = new Creator(props);
        String jwt = creator.createSignedJWT(captcha.getAnswer());

        // wait for current second to pass
        Thread.sleep(1000);

        // expect exception upon verification
        Verifier verifier = new Verifier(props);
        Assertions.assertThrows(VerificationException.class,
                () -> verifier.verify(jwt, captcha.getAnswer())
        );
    }

}
