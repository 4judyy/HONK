import java.awt.*;
import javax.swing.*;

/**
 * Round Icon class is used to create custom round icons
 * @author Di Zhou
 */
public class RoundIcon implements Icon {
    /** custom width of round icon */
    private int width;
    /** custom height of round icon */
    private int height;
    /** custom arc width of round icon */
    private int arcWidth;
    /** custom arc height of round icon */
    private int arcHeight;
    /** custom color of round icon */
    private Color color;
    /** custom degree of round icon */
    private double degrees;
    /** custom color border of round icon */
    private Color borderColor;
    /** custom border width of round icon */
    private int borderWidth;

    /**
     * Constructor to create a custom round icon, without degrees
     * @param width width of round icon
     * @param height height of round icon
     * @param arcWidth arc width of round icon
     * @param arcHeight arc height of round icon
     * @param color color of round icon
     * @param borderColor border color of round icon
     * @param borderWidth border width of round icon
     */
    public RoundIcon(int width, int height, int arcWidth, int arcHeight, Color color, Color borderColor, int borderWidth) {
        this.width = width;
        this.height = height;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.color = color;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    /**
     * Constructor to create a custom round icon, with degrees
     * @param width width of round icon
     * @param height height of round icon
     * @param arcWidth arc width of round icon
     * @param arcHeight arc height of round icon
     * @param color color of round icon
     * @param degrees degrees of round icon
     * @param borderColor border color of round icon
     * @param borderWidth border width of round icon
     */
    public RoundIcon(int width, int height, int arcWidth, int arcHeight, Color color, double degrees, Color borderColor, int borderWidth) {
        this.width = width;
        this.height = height;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.color = color;
        this.degrees = degrees;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    /**
     * Override method to paint the icon
     * @param c  a {@code Component} to get properties useful for painting
     * @param g  the graphics context
     * @param x  the X coordinate of the icon's top-left corner
     * @param y  the Y coordinate of the icon's top-left corner
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Set anti-aliasing for smooth rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Translate and rotate the graphics context
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(degrees));

        // Draw the border
        if (borderWidth > 0 && borderColor != null) {
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRoundRect(-width / 2, -height / 2, width, height, arcWidth, arcHeight);
        }

        // Draw the rounded rectangle
        g2d.setColor(color);
        g2d.fillRoundRect(-width / 2, -height / 2, width, height, arcWidth, arcHeight);

        g2d.dispose();
    }

    /**
     * getter method for the icon width
     * @return icon width
     */
    @Override
    public int getIconWidth() {
        return width;
    }

    /**
     * getter method for the icon height
     * @return icon height
     */
    @Override
    public int getIconHeight() {
        return height;
    }

    /**
     * getter method for the icon color
     * @return icon color
     */
    public Color getColor() {
        return color;
    }

    /**
     * setter method for the icon color
     * @param color new color of icon
     */
    public void setColor(Color color) {
        this.color = color;
    }
}