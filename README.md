# Captcha4J

Tiny java library for generating images and audio-fragments for use in Captcha's. 

Supports internationalisation for audio. 


## Usage

### Build captcha

```java
Captcha captcha = new Captcha.Builder(200, 50)
        .addText()
        .addNoise()
        .addBackground()
        .build();
```

This project is forked from Stateless-captcha 1.2.1 (https://github.com/sdtool/stateless-captcha), which was in turn based on SimpleCaptcha 
version 1.2.1 (http://simplecaptcha.sourceforge.net/). There are significant backwards-incompatible changes breaking compatibility with the
original works (no support for mix-and-match :)). 