package com.github.captcha4j.core.text.gimpy;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Stretch the given image over the X and Y axes. If no scale is given,
 * defaults to an X scale of 1.0 and a Y scale of 3.0 (i.e. make the image
 * tall but do not affect the width).
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class StretchGimpyRenderer implements GimpyRenderer {

    /**
     * X axis default scaling
     */
    private static final double XDEFAULT = 1.0;
    /**
     * Y axis default scaling
     */
    private static final double YDEFAULT = 3.0;

    /**
     * The X scaling
     */
    private final double _xScale;
    /**
     * The Y scaling
     */
    private final double _yScale;

    /**
     * Default constructor
     */
    public StretchGimpyRenderer() {
        this(XDEFAULT, YDEFAULT);
    }

    /**
     * Constructor with customized x scale and y scale
     *
     * @param xScale the x axis scaling
     * @param yScale the y axis scaling
     */
    public StretchGimpyRenderer(double xScale, double yScale) {
        _xScale = xScale;
        _yScale = yScale;
    }

    /**
     * Applies the rendering to the image
     *
     * @param image the image
     */
    @Override
    public void gimp(BufferedImage image) {
        Graphics2D g = image.createGraphics();
        AffineTransform at = new AffineTransform();
        at.scale(_xScale, _yScale);
//		RenderingHints hints = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawRenderedImage(image, at);
    }

}
