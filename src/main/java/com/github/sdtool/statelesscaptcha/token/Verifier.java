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
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.sdtool.statelesscaptcha.exception.VerificationException;

/**
 * The token verifier
 *
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class Verifier {

    /**
     * The token properties
     * Initiated to default
     */
    private TokenProperties properties = new TokenProperties();

    /**
     * Default constructor
     */
    public Verifier() {
    }

    /**
     * Constructor with customized token properties
     *
     * @param properties the token properties to use
     */
    public Verifier(TokenProperties properties) {
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
     * Verify captcha
     *
     * @param jwtToken
     * @param answer
     */
    public void verify(String jwtToken, String answer) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(answer);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(properties.getIssuer())
                    .build();
            verifier.verify(jwtToken);
        } catch (JWTVerificationException exception) {
            throw new VerificationException(exception.getMessage());
        }
    }

}
