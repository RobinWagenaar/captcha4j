package com.github.captcha4j.core.util;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

/**
 * Image Util
 * Filters image with given bufferedImageOp
 *
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class  ImageUtil {

    /**
     * Applies the image op filter to image
     *
     * @param img    the image to apply to
     * @param filter the filter to apply
     */
    public static void applyFilter(BufferedImage img, BufferedImageOp filter) {
        BufferedImage newImage = filter.filter(img, null);
        img.setData(newImage.getRaster());
    }
}
