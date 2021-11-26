# Stateless Captcha for Java

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.sdtool/stateless-captcha/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.sdtool/stateless-captcha)

Simple standalone stateless captcha library for Java 8 (and above). Stateless verification implementation for custom usage (such as
spring boot stateless microservice mode).


This project is forked from Stateless-captcha 1.2.1 (https://github.com/sdtool/stateless-captcha), which was in turn based on SimpleCaptcha 
version 1.2.1 (http://simplecaptcha.sourceforge.net/). There are significant backwards-incompatible changes breaking compatibility with the
original works (no support for mix-and-match :)). 

## Usage

### Build captcha

```java
Captcha captcha=new Captcha.Builder(200, 50)
        .addText()
        .addNoise()
        .addBackground()
        .build();
```

### Build Token and Verify

```java
TokenProperties props = new TokenProperties("sd", 300)

// Create token (creator side, can be in any microservice/application)
Creator creator = new Creator(props);
// build token with captcha from earlier stage
CaptchaToken token = creator.create(captcha);

// verify token (verifier side, can be in any other microservice/application)
CaptchaVerificationToken verification=<user side data>
Verifier verifier = Verifier(props);
// verify token. If invalid VerificationException is thrown
verifier.verify(verification);
```