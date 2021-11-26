package com.github.captcha4j.core.text.gimpy;

import com.github.captcha4j.core.util.ImageUtil;
import com.jhlabs.image.BlockFilter;

import java.awt.image.BufferedImage;

/**
 * Block render
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class BlockGimpyRenderer implements GimpyRenderer {

    /**
     * The default block size
     */
    private static final int DEF_BLOCK_SIZE = 3;
    /**
     * The block size
     */
    private final int _blockSize;

    /**
     * Default constructor
     */
    public BlockGimpyRenderer() {
        this(DEF_BLOCK_SIZE);
    }

    /**
     * The constructor with customized block size
     *
     * @param blockSize the block size
     */
    public BlockGimpyRenderer(int blockSize) {
        _blockSize = blockSize;
    }

    /**
     * Applies the rendering to the image
     *
     * @param image the image
     */
    @Override
    public void gimp(BufferedImage image) {
        BlockFilter filter = new BlockFilter();
        filter.setBlockSize(_blockSize);
        ImageUtil.applyFilter(image, filter);
    }

}
