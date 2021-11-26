package com.github.captcha4j.core.text.gimpy;

import com.github.captcha4j.core.util.ImageUtil;
import com.jhlabs.image.ShadowFilter;

import java.awt.image.BufferedImage;

/**
 * Adds a dark drop-shadow.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class DropShadowGimpyRenderer implements GimpyRenderer {

    /**
     * The default radius
     */
    private static final int DEFAULT_RADIUS = 3;
    /**
     * The default opacity
     */
    private static final int DEFAULT_OPACITY = 75;

    /**
     * The radius
     */
    private final int _radius;
    /**
     * The opacity
     */
    private final int _opacity;

    /**
     * Default constructor
     */
    public DropShadowGimpyRenderer() {
        this(DEFAULT_RADIUS, DEFAULT_OPACITY);
    }

    /**
     * Constructor with customized radius and opacity
     *
     * @param radius  the radius
     * @param opacity the opacity
     */
    public DropShadowGimpyRenderer(int radius, int opacity) {
        _radius = radius;
        _opacity = opacity;
    }

    /**
     * Applies the rendering to the image
     *
     * @param image the image
     */
    @Override
    public void gimp(BufferedImage image) {
        ShadowFilter sFilter = new ShadowFilter();
        sFilter.setRadius(_radius);
        sFilter.setOpacity(_opacity);
        ImageUtil.applyFilter(image, sFilter);
    }

}