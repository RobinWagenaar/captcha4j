package com.github.captcha4j.core.image.renderer;

import com.github.captcha4j.core.util.FileUtil;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Renders a text onto the image.
 *
 * @author <a href="mailto:robin.wagenaar@gmail.com">Robin Wagenaar</a>
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class DefaultWordRenderer implements WordRenderer {

    private Color color = Color.BLACK;
    private Font font;

    /**
     * The defaults are:
     * - Color: black
     * - Font: Verily
     *
     * Will attempt to find a suitable font size, depending on the dimensions of
     * the image and the font selected. Retains a padding of about 5% of the image
     * width.
     */
    public DefaultWordRenderer(){
        try {
            font = Font.createFont(EmbeddedFont.WHITE_RABBIT.getType(), FileUtil.readResource(EmbeddedFont.WHITE_RABBIT.getFilename()));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Construct a <code>WordRenderer</code> using the given <code>Color</code> and
     * <code>Font</code>.
     *
     * @param color
     * @param font
     */
    public DefaultWordRenderer(Color color, Font font) {
        this.color = color;
        this.font = font;
    }

    /**
     * Render a word onto a BufferedImage.
     *
     * @param word  The word to be rendered.
     * @param image The BufferedImage onto which the word will be painted.
     */
    @Override
    public void render(final String word, BufferedImage image) {
        Graphics2D g = image.createGraphics();

        RenderingHints hints = new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g.setRenderingHints(hints);

        int padding = image.getWidth()/100*5; //5% of width
        float maxFontSize = calcMaxFontSize(word, font, image, padding, g);

        g.setColor(this.color);
        g.setFont(this.font.deriveFont(maxFontSize));
        FontMetrics fm = g.getFontMetrics();

        int xOffset = ((image.getWidth() - fm.stringWidth(word)) / 2);
        int yOffset = ((image.getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(word, xOffset, yOffset);
    }

    private float calcMaxFontSize(String text, Font f, BufferedImage img, int padding, Graphics2D g){
        int maxTextWidth = img.getWidth() - (2*padding);
        int maxTextHeight = img.getHeight() - (2*padding);

        float currentFontSize = 8f;
        int currentTextWidth = 0;
        int currentTextHeight = 0;

        while (currentTextWidth < maxTextWidth && currentTextHeight < maxTextHeight) {
            currentFontSize += 2f;
            Font resizedFont = f.deriveFont(currentFontSize);
            FontRenderContext frc = g.getFontRenderContext();
            Rectangle2D bb = new TextLayout(text, resizedFont, frc).getBounds();
            currentTextWidth = (int) bb.getWidth();
            currentTextHeight = (int) bb.getHeight();
        }

        return currentFontSize - 2f;
    }
}
