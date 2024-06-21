import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Create and display a custom dialogue popup
 * <p>
 *      The display of the custom dialogue is used for login page, main map page as well as the settings page.
 *      Creates a aesthetically pleasing popup.
 * </p>
 * @author Di Zhou
 */
public class CustomDialog {

    /**
     * This method creates a popup with the given specified message, image, title, message type and colour.
     * @param message message text
     * @param imagePath file path of image
     * @param title title of popup
     * @param messageType type of message
     * @param color color of pop up
     */
    public void createPopup(String message, String imagePath, String title, int messageType, Color color) {
        try {
            // Load the background image
            BufferedImage backgroundImage = ImageIO.read(new File(imagePath));

            File PixelHugger = new File("media/PixelHugger.otf");
            Font pixel = Font.createFont(Font.TRUETYPE_FONT, PixelHugger);

            // Create a panel with the background image
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            };
            panel.setLayout(null);
            panel.setPreferredSize(new Dimension(600, 400)); // Set the preferred size of the panel
            panel.setBorder(BorderFactory.createEmptyBorder());
            panel.setBackground(color);

            // Create a label to display the message
            JTextPane text = new JTextPane();
            text.setEditable(false);
            text.setCursor(null);
            text.setFont(pixel.deriveFont(Font.PLAIN, 18));
            text.setForeground(color);
            text.setOpaque(false);
            text.setAlignmentX(SwingConstants.CENTER);
            text.setBounds(216, 280, 200, 100);
            text.setText(message);
            panel.add(text);

            RoundIcon square = new RoundIcon(0,0,0,0,color, color, 0);

            // Set UIManager properties for the option pane
            UIManager.put("OptionPane.background", color);
            UIManager.put("Panel.background", color);
            UIManager.put("OptionPane.buttonFont" , pixel.deriveFont(0,22));
            UIManager.put("Button.background", color);
            UIManager.put("Button.border", BorderFactory.createEmptyBorder());
            UIManager.put("Button.foreground", Color.black);

            // Show the message dialog with the custom panel and no icon

            JOptionPane.showMessageDialog(null, panel, title, messageType, square);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * default custom dialog constructor.
     */
    public CustomDialog() {}
}