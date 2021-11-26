package com.github.captcha4j.core.image.renderer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Colored edges word renderer
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class ColoredEdgesWordRenderer implements WordRenderer {

    /**
     * The secure random
     */
    private static final Random RAND = new SecureRandom();
    /**
     * List of default colors
     */
    private static final List<Color> DEFAULT_COLORS = new ArrayList<>();
    /**
     * List of default fonts
     */
    private static final List<Font> DEFAULT_FONTS = new ArrayList<>();
    /**
     * List of wtroke widths
     */
    private static final float DEFAULT_STROKE_WIDTH = 0f;
    // The text will be rendered 25%/5% of the image height/width from the X and Y axes
    /**
     * The Y offset
     */
    private static final double YOFFSET = 0.25;
    /**
     * The x offset
     */
    private static final double XOFFSET = 0.05;

    static {
        DEFAULT_FONTS.add(new Font("Arial", Font.BOLD, 40));
        DEFAULT_COLORS.add(Color.BLACK);
    }

    /**
     * The list of fonts
     */
    private final List<Font> _fonts;
    /**
     * List of colors
     */
    private final List<Color> _colors;
    /**
     * Stroke width
     */
    private final float _strokeWidth;

    /**
     * Use the default color (black) and font (Arial).
     */
    public ColoredEdgesWordRenderer() {
        this(DEFAULT_COLORS, DEFAULT_FONTS, DEFAULT_STROKE_WIDTH);
    }

    /**
     * Build a <code>WordRenderer</code> using the given <code>Color</code>s and
     * <code>Font</code>s.
     *
     * @param colors List of colors
     * @param fonts  List of fonts
     */
    public ColoredEdgesWordRenderer(List<Color> colors, List<Font> fonts) {
        this(colors, fonts, DEFAULT_STROKE_WIDTH);
    }

    /**
     * Build a <code>WordRenderer</code> using the given <code>Color</code>s and
     * <code>Font</code>s and <code>Stroke Width</code>.
     *
     * @param colors      List of colors
     * @param fonts       List of fonts
     * @param strokeWidth Stroke width
     */
    public ColoredEdgesWordRenderer(List<Color> colors, List<Font> fonts, float strokeWidth) {
        _colors = colors != null ? colors : DEFAULT_COLORS;
        _fonts = fonts != null ? fonts : DEFAULT_FONTS;
        _strokeWidth = strokeWidth < 0 ? DEFAULT_STROKE_WIDTH : strokeWidth;
    }

    /**
     * Renders the word
     *
     * @param word  The sequence of characters to be rendered.
     * @param image The image onto which the word will be rendered.
     */
    @Override
    public void render(final String word, BufferedImage image) {
        Graphics2D g = image.createGraphics();

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        g.setRenderingHints(hints);

        AttributedString as = new AttributedString(word);
        as.addAttribute(TextAttribute.FONT, getRandomFont());

        FontRenderContext frc = g.getFontRenderContext();
        AttributedCharacterIterator aci = as.getIterator();

        TextLayout tl = new TextLayout(aci, frc);
        int xBaseline = (int) Math.round(image.getWidth() * XOFFSET);
        int yBaseline = image.getHeight() - (int) Math.round(image.getHeight() * YOFFSET);
        Shape shape = tl.getOutline(AffineTransform.getTranslateInstance(xBaseline, yBaseline));

        g.setColor(getRandomColor());
        g.setStroke(new BasicStroke(_strokeWidth));

        g.draw(shape);
    }

    /**
     * Gets a random color
     *
     * @return the color
     */
    private Color getRandomColor() {
        return (Color) getRandomObject(_colors);
    }

    /**
     * Gsts a random font
     *
     * @return the font
     */
    private Font getRandomFont() {
        return (Font) getRandomObject(_fonts);
    }

    /**
     * Gets a random object from a list of objects
     *
     * @param objs List of objects to refer to
     * @return the object
     */
    private Object getRandomObject(List<?> objs) {
        if (objs.size() == 1) {
            return objs.get(0);
        }

        int i = RAND.nextInt(objs.size());
        return objs.get(i);
    }
}
