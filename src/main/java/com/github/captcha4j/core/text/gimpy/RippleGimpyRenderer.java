package com.github.captcha4j.core.text.gimpy;

import com.github.captcha4j.core.util.ImageUtil;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.TransformFilter;

import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class RippleGimpyRenderer implements GimpyRenderer {

    /**
     * Applies the rendering to the image
     *
     * @param image the image
     */
    @Override
    public void gimp(BufferedImage image) {
        RippleFilter filter = new RippleFilter();
        filter.setWaveType(RippleFilter.SAWTOOTH);
        filter.setXAmplitude(2.6f);
        filter.setYAmplitude(1.7f);
        filter.setXWavelength(15);
        filter.setYWavelength(5);

        filter.setEdgeAction(TransformFilter.CLAMP);

        ImageUtil.applyFilter(image, filter);
    }

}