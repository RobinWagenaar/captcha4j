package com.github.captcha4j.core.image;

import com.github.captcha4j.core.Captcha;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A builder for generating a CAPTCHA image/answer pair.
 *
 * <p>
 * Example for generating a new CAPTCHA:
 * </p>
 * <pre>Captcha captcha = new Captcha.Builder(200, 50)
 * 	.addText()
 * 	.addBackground()
 * 	.build();</pre>
 * <p>Note that the <code>build()</code> must always be called last. Other methods are optional,
 * and can sometimes be repeated. For example:</p>
 * <pre>Captcha captcha = new Captcha.Builder(200, 50)
 * 	.addText()
 * 	.addNoise()
 * 	.addNoise()
 * 	.addNoise()
 * 	.addBackground()
 * 	.build();</pre>
 * <p>Adding multiple backgrounds has no affect; the last background added will simply be the
 * one that is eventually rendered.</p>
 *
 * @author <a href="mailto:robin.wagenaar@gmail.com">Robin Wagenaar</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 */
public class ImageCaptcha implements Captcha {

    String answer;
    BufferedImage image;

    public ImageCaptcha(String answer, BufferedImage image){
        this.answer = answer;
        this.image = image;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public ByteArrayOutputStream getData(){
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(this.getImage(), "png", os);
            return os;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDataType() {
        return "image/png";
    }


    public BufferedImage getImage() {
        return image;
    }
}
