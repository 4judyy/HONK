import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * The main map class is responsible for the functionality and creation of the main map screen.
 * <p>
 *     This class sets up the user interface of the main map page.
 *     Allows for users to move to different screens through buttons.
 *     This class acts as the main menu of the game
 * </p>
 * @author Tyler Charles Inwood
 */
public class Tutorial extends JPanel {
    /** display object */
    private Display display;
    /** background image for tutorial */
    private Image background;
    /** custom font */
    private Font pixelFont;

    /**
     * Constructs a new Tutorial panel.
     * @param display The display containing this panel
     * @throws FileNotFoundException if an image file is not found
     */
    public Tutorial(Display display) throws FileNotFoundException {
        this.display = display;
        setLayout(null);

        // Load the background image
        try {
            background = ImageIO.read(new File("media/tutorialpage.png"));
        } catch (IOException e) {
            System.out.println("Could not find image");
        }

        //Load pixelhugger
        try {
            File pixelHuggerFile = new File("media/PixelHugger.otf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, pixelHuggerFile).deriveFont(Font.PLAIN, 39);
        } catch (IOException | FontFormatException e) {
            // Handle font loading errors
            e.printStackTrace();
        }

        createActionButtons();
        createLabels();

        // Make the escape key exit (same as back to game button)
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escapePressed");
        ActionMap actionMap = getActionMap();
        actionMap.put("escapePressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen("MainMap");
            }
        });
    }

    /**
     * Creates action buttons helper method
     * uses create actions buttons helper method
     */
    private void createActionButtons() {

        // Action buttons with common attributes
        JButton[] actionButtons = {
                createActionButton("back",724,20,194,77, Color.WHITE, new Color(0,0,0,0), new Font("Helvetica", Font.BOLD, 44))

        };

        actionButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen("MainMap");
            }
        });

        // Add action buttons to the panel
        for (JButton button : actionButtons) {
            add(button);
        }
    }

    /**
     * Create action button helper method
     * given params, creates a specific action button
     * @param text title of action button
     * @param x coordinate of action button
     * @param y coordinate of action button
     * @param width of action button
     * @param height of action button
     * @param foreground color of foreground of action button
     * @param background color of background of action button
     * @param font used for text of action button
     * @return unique action button created with parameters
     */
    private JButton createActionButton(String text, int x, int y, int width, int height, Color foreground, Color background, Font font) {
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setBounds(x, y, width, height);
        button.setBackground(background);
        button.setFont(font);
        button.setForeground(foreground);

        return button;
    }

    /**
     * Creates a rounded corner label with given specifications as parameters
     * @param labelX x coordinate of label
     * @param labelY y coordinate of label
     * @param labelWidth width of label
     * @param labelHeight height of label
     * @param stickerWidth width of sticker
     * @param stickerHeight height of sticker
     * @param arcWidth width of the arc
     * @param arcHeight height of the arc
     * @param fillColor background color of the label
     * @param degrees degree of the label corner
     * @param borderColor color of the label border
     * @param borderWidth width of the label border
     */
    private void addRoundedCornerLabel(int labelX, int labelY, int labelWidth, int labelHeight, int stickerWidth, int stickerHeight, int arcWidth, int arcHeight, Color fillColor, double degrees, Color borderColor, int borderWidth) {
        RoundIcon icon = new RoundIcon(stickerWidth, stickerHeight, arcWidth, arcHeight, fillColor, degrees, borderColor, borderWidth);
        JLabel label = new JLabel("", icon, JLabel.LEFT);
        label.setBounds(labelX, labelY, labelWidth + borderWidth, labelHeight + borderWidth); // Adjust bounds to include border width
        add(label);
    }

    /**
     * Method to create labels
     */
    private void createLabels() {

        // Custom label for Settings
        CustomJLabel tutorialTitle = new CustomJLabel("Tutorial", -1.6);
        tutorialTitle.setFont(new Font("Helvetica", Font.BOLD, 67));
        tutorialTitle.setOpaque(false);
        tutorialTitle.setBounds(88, 66, 300, 100);
        tutorialTitle.setLetterSpacing(-3);
        tutorialTitle.setForeground(Color.WHITE);
        add(tutorialTitle);

        // Custom label for Coming Soon
        CustomJLabel tutorialText = new CustomJLabel("Talbot & natural science tutorials coming soon", 0);
        tutorialText.setFont(pixelFont.deriveFont(0, 25));
        tutorialText.setOpaque(false);
        tutorialText.setBounds(194, 620, 573, 54);
        tutorialText.setLetterSpacing(-1);
        tutorialText.setForeground(Color.WHITE);
        add(tutorialText);

        addRoundedCornerLabel(23,25,404,150,370,110,90,90,new Color(255, 157, 112),-1.6,Color.WHITE,0); // Settings label
        addRoundedCornerLabel(743, 24, 165, 64, 161, 62, 64, 64, new Color(37, 165, 92), 0, Color.WHITE,0); // back button
        addRoundedCornerLabel(152, 600, 700, 80, 658, 70, 64, 64, new Color(255, 157, 112), 0, Color.WHITE,0); // special button
    }


    /**
     * Show Screen Method.
     * Uses show method in display to show screen with given name
     * Called when switching from one page to another.
     * @param name of screen in which to switch to
     */
    private void showScreen(String name){
        display.show(name);
    }

    /**
     * used to paint the background image
     * @param g the <code>Graphics</code> object to protect
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image at (0, 0) with the same size as the panel
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

}