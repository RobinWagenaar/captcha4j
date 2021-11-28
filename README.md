# Captcha4J

Tiny java library for generating images and audio-fragments for use in Captcha's. 

- Easy configuration and customization of the difficulty level.
- Supports internationalization for audio, with several English and Dutch voices included.
- Minimal number of third party libraries required. 

## Usage

### Build captcha
```java
ImageCaptcha imageCaptcha = new ImageCaptchaBuilder(200, 50)
        .addAnswer("123abc")
        .addNoise()
        .addBackground()
        .build();
BufferedImage image = imageCaptcha.getImage()

AudioCaptcha audioCaptcha = new AudioCaptchaBuilder()
        .addRandomAnswer()
        .addVoice(Language.NL)
        .addNoise()
        .build();
AudioInputStream stream = audioCaptcha.getAudio().getAudioInputStream();
```

### Example output:
![captcha-example-1](https://github.com/RobinWagenaar/captcha4j/raw/main/captcha4j-examples/captcha-1.png)
![captcha-example-2](https://github.com/RobinWagenaar/captcha4j/raw/main/captcha4j-examples/captcha-2.png)
![captcha-example-4](https://github.com/RobinWagenaar/captcha4j/raw/main/captcha4j-examples/captcha-4.png)
![captcha-example-3](https://github.com/RobinWagenaar/captcha4j/raw/main/captcha4j-examples/captcha-3.png)


## Credits
This project is forked from Stateless-captcha 1.2.1 (https://github.com/sdtool/stateless-captcha), which was in turn based on SimpleCaptcha 
version 1.2.1 (http://simplecaptcha.sourceforge.net/). There are significant backwards-incompatible changes breaking compatibility with the
original works (mix-and-match is not recommended :)). 