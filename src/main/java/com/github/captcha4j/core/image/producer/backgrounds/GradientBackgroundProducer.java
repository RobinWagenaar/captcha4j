package com.github.captcha4j.core.image.producer.backgrounds;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Creates a gradiant background with the given <i>from</i> and <i>to</i>
 * Color values. If none are specified they default to light gray and white
 * respectively.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class GradientBackgroundProducer implements BackgroundProducer {

    /**
     * The from color
     */
    private Color _fromColor;
    /**
     * The to color
     */
    private Color _toColor;

    /**
     * Default constructor
     */
    public GradientBackgroundProducer() {
        this(Color.DARK_GRAY, Color.WHITE);
    }

    /**
     * Constructor with customized from and to colors
     *
     * @param from the from color
     * @param to   the to color
     */
    public GradientBackgroundProducer(Color from, Color to) {
        _fromColor = from;
        _toColor = to;
    }

    /**
     * Add the background to the given image.
     *
     * @param image The image onto which the background will be rendered.
     * @return The image with the background rendered.
     */
    @Override
    public BufferedImage addBackground(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        return getBackground(width, height);
    }

    /**
     * Gets the background
     *
     * @param width  the width
     * @param height the height
     * @return The image with the background rendered.
     */
    @Override
    public BufferedImage getBackground(int width, int height) {
        // create an opaque image
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = img.createGraphics();
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHints(hints);

        // create the gradient color
        GradientPaint ytow = new GradientPaint(0, 0, _fromColor, width, height,
                _toColor);

        g.setPaint(ytow);
        // draw gradient color
        g.fill(new Rectangle2D.Double(0, 0, width, height));

        // draw the transparent image over the background
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return img;
    }

    /**
     * Sets the from/starting color
     *
     * @param fromColor the from/starting color
     */
    public void setFromColor(Color fromColor) {
        _fromColor = fromColor;
    }

    /**
     * Sets the to/ending color
     *
     * @param toColor the to/ending color
     */
    public void setToColor(Color toColor) {
        _toColor = toColor;
    }

}
