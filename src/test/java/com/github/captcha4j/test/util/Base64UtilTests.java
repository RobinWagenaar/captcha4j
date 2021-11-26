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

package com.github.captcha4j.test.util;

import com.github.captcha4j.core.audio.AudioCaptcha;
import com.github.captcha4j.util.Base64Util;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFileFormat;
import java.io.IOException;

public class Base64UtilTests {

    @Test
    public void testAudioConversion() throws IOException {
        AudioCaptcha captcha = new AudioCaptcha.Builder()
                .addAnswer(() -> "123")
                .addNoise()
                .addVoice()
                .build();
        Base64Util.encodeAudio(captcha.getChallenge().getAudioInputStream(),
                AudioFileFormat.Type.WAVE);
    }

}
