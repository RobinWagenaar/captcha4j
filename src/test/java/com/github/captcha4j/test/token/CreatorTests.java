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

package com.github.captcha4j.test.token;

import com.github.captcha4j.core.audio.AudioCaptcha;
import com.github.captcha4j.core.audio.AudioCaptchaBuilder;
import com.github.captcha4j.core.image.ImageCaptcha;
import com.github.captcha4j.core.image.ImageCaptchaBuilder;
import org.junit.jupiter.api.Test;

public class CreatorTests {

    @Test
    public void testCaptchaTokenCreation() {
        ImageCaptcha captcha = new ImageCaptchaBuilder(200, 100)
                .addRandomAnswer()
                .addNoise()
                .addBorder()
                .build();
    }

    @Test
    public void testAudioCaptchaTokenCreation() {
        AudioCaptcha captcha = new AudioCaptchaBuilder()
                .addRandomAnswer()
                .addNoise()
                .addVoice()
                .build();
    }

}
