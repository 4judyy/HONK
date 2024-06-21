import javax.swing.*;
import java.awt.*;

/**
 * CustomJLabel adds custom functions to render text with spacing and rotation.
 * <br><br>
 * Allows for more control in appearance for visuals <br> <br>
 * @author Di Zhou
 */
public class CustomJLabel extends JLabel {
    /** set letter spacing */
    private int letterSpacing = 0;
    /** adjusts angle */
    private double angle;

    /**
     * Construct a custom J label with specific text and angle
     * @param text displayed in the label
     * @param angle angle of the label
     */
    public CustomJLabel(String text, double angle) {
        super(text);
        this.angle = angle;
    }

    /**
     * Overrides paintComponent method in Jlabel.
     * Used for text rendering  to apply letter spacing and angle rotation ot text.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (letterSpacing != 0) {
            // Set color
            g2d.setColor(getForeground());

            // Apply rotation
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            g2d.translate(centerX, centerY);
            g2d.rotate(Math.toRadians(angle));
            g2d.translate(-centerX, -centerY);

            // Set font
            g2d.setFont(getFont());

            // Set letter spacing
            FontMetrics fm = g2d.getFontMetrics();
            int x = 0;
            for (char ch : getText().toCharArray()) {
                g2d.drawString(String.valueOf(ch), x, fm.getAscent());
                x += fm.charWidth(ch) + letterSpacing; // Add spacing between characters
            }
        } else {
            super.paintComponent(g2d);
        }
        g2d.dispose();
    }

    /**
     * Method to set letter spacing
     * @param spacing of the text
     */
    public void setLetterSpacing(int spacing) {
        this.letterSpacing = spacing;
        repaint();
    }
}
