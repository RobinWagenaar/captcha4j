package com.github.captcha4j.core.image;

import com.github.captcha4j.core.image.gimpy.GimpyRenderer;
import com.github.captcha4j.core.image.gimpy.RippleGimpyRenderer;
import com.github.captcha4j.core.image.producer.DefaultTextProducer;
import com.github.captcha4j.core.image.producer.TextProducer;
import com.github.captcha4j.core.image.producer.backgrounds.BackgroundProducer;
import com.github.captcha4j.core.image.producer.backgrounds.TransparentBackgroundProducer;
import com.github.captcha4j.core.image.producer.noise.CurvedLineNoiseProducer;
import com.github.captcha4j.core.image.producer.noise.NoiseProducer;
import com.github.captcha4j.core.image.renderer.DefaultWordRenderer;
import com.github.captcha4j.core.image.renderer.WordRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCaptchaBuilder {

    private String answer = "";
    private BufferedImage img;
    private BufferedImage bg;
    private boolean addBorder = false;

    /**
     * Constructor with customized width and height
     *
     * @param width  the width
     * @param height the height
     */
    public ImageCaptchaBuilder(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Add a background using the default {@link BackgroundProducer} (a {@link TransparentBackgroundProducer}).
     *
     * @return the Builder
     */
    public ImageCaptchaBuilder addBackground() {
        return addBackground(new TransparentBackgroundProducer());
    }

    /**
     * Add a background using the given {@link BackgroundProducer}.
     *
     * @param bgProd Background Producer
     * @return the Builder
     */
    public ImageCaptchaBuilder addBackground(BackgroundProducer bgProd) {
        bg = bgProd.getBackground(img.getWidth(), img.getHeight());

        return this;
    }

    /**
     * Generate the answer to the CAPTCHA using the {@link DefaultTextProducer}.
     *
     * @return the Builder
     */
    public ImageCaptchaBuilder addRandomAnswer() {
        return addAnswer(new DefaultTextProducer());
    }

    /**
     * Generate the answer to the CAPTCHA using the given
     * {@link TextProducer}.
     *
     * @param txtProd TextProducer
     * @return the Builder
     */
    public ImageCaptchaBuilder addAnswer(TextProducer txtProd) {
        return addText(txtProd, new DefaultWordRenderer());
    }

    /**
     * Generate the answer to the CAPTCHA using the default
     * {@link TextProducer}, and render it to the image using the given
     * {@link WordRenderer}.
     *
     * @param wRenderer WordRenderer
     * @return the Builder
     */
    public ImageCaptchaBuilder addText(WordRenderer wRenderer) {
        return addText(new DefaultTextProducer(), wRenderer);
    }

    /**
     * Generate the answer to the CAPTCHA using the given
     * {@link TextProducer}, and render it to the image using the given
     * {@link WordRenderer}.
     *
     * @param txtProd   TextProducer
     * @param wRenderer WordRenderer
     * @return the Builder
     */
    public ImageCaptchaBuilder addText(TextProducer txtProd, WordRenderer wRenderer) {
        answer += txtProd.getText();
        wRenderer.render(answer, img);

        return this;
    }

    /**
     * Add noise using the default {@link NoiseProducer} (a {@link CurvedLineNoiseProducer}).
     *
     * @return the Builder
     */
    public ImageCaptchaBuilder addNoise() {
        return this.addNoise(new CurvedLineNoiseProducer());
    }

    /**
     * Add noise using the given NoiseProducer.
     *
     * @param nProd NoiseProducer
     * @return the Builder
     */
    public ImageCaptchaBuilder addNoise(NoiseProducer nProd) {
        nProd.makeNoise(img);
        return this;
    }

    /**
     * Gimp the image using the default {@link GimpyRenderer} (a {@link RippleGimpyRenderer}).
     *
     * @return the Builder
     */
    public ImageCaptchaBuilder gimp() {
        return gimp(new RippleGimpyRenderer());
    }

    /**
     * Gimp the image using the given {@link GimpyRenderer}.
     *
     * @param gimpy GimpyRenderer
     * @return the Builder
     */
    public ImageCaptchaBuilder gimp(GimpyRenderer gimpy) {
        gimpy.gimp(img);
        return this;
    }

    /**
     * Draw a single-pixel wide black border around the image.
     *
     * @return the Builder
     */
    public ImageCaptchaBuilder addBorder() {
        addBorder = true;
        return this;
    }

    /**
     * Build the CAPTCHA. This method should always be called, and should always
     * be called last.
     *
     * @return The constructed CAPTCHA.
     */
    public ImageCaptcha build() {
        if (bg == null) {
            bg = new TransparentBackgroundProducer().getBackground(img.getWidth(), img.getHeight());
        }

        // Paint the main image over the background
        Graphics2D g = bg.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g.drawImage(img, null, null);

        if (addBorder) {
            int width = img.getWidth();
            int height = img.getHeight();

            g.setColor(Color.BLACK);

            //noinspection SuspiciousNameCombination
            g.drawLine(0, 0, 0, width);
            g.drawLine(0, 0, width, 0);
            g.drawLine(0, height - 1, width, height - 1);
            g.drawLine(width - 1, height - 1, width - 1, 0);
        }

        img = bg;

        return new ImageCaptcha(answer, img);
    }

}
