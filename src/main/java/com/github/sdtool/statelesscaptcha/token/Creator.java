/*
 *    Copyright 2021 Subhajit Das
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

package com.github.sdtool.statelesscaptcha.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.github.sdtool.statelesscaptcha.core.audio.AudioCaptcha;
import com.github.sdtool.statelesscaptcha.core.text.Captcha;
import com.github.sdtool.statelesscaptcha.exception.CreationException;
import com.github.sdtool.statelesscaptcha.util.Base64Util;

import javax.sound.sampled.AudioFileFormat;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * The token creator
 *
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class Creator {

    /**
     * The token properties
     * Initiated to default
     */
    private TokenProperties properties = new TokenProperties();

    /**
     * Default constructor
     */
    public Creator() {
    }

    /**
     * Constructor with customized token properties
     *
     * @param properties the token properties to use
     */
    public Creator(TokenProperties properties) {
        this.properties = properties;
    }

    /**
     * Gets the token properties
     *
     * @return the token properties
     */
    public TokenProperties getProperties() {
        return properties;
    }

    /**
     * Sets the token properties
     *
     * @param properties the token properties
     */
    public void setProperties(TokenProperties properties) {
        this.properties = properties;
    }


    /**
     * Generate a JsonWebToken and sign it based on a secret
     */
    public String createSignedJWT(String secret){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        LocalDateTime now = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        LocalDateTime exp = now.plusSeconds(properties.getValidity());

        return JWT.create()
                .withExpiresAt(Date.from(exp.atZone(ZoneId.systemDefault()).toInstant()))
                .withIssuer(properties.getIssuer())
                .sign(algorithm);
    }

}
