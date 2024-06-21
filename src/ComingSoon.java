import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Coming soon class used to paint the coming soon pages for unfinished mini-games
 * <br><br>
 * Creates a custom panel to display a custom coming soon page with specific design.<br><br>
 * <p>
 *     Class designs include background image, title and labels,
 *     accent colors, action buttons.
 * </p>
 * @author Di Zhou
 */
public class ComingSoon extends JPanel {
    /** display objecct */
    private Display display;
    /** image background */
    private Image background;
    /** font of characters on screen */
    private Font pixelFont;
    /** what the location it is */
    private String location;
    /** subtext string */
    private String subtext;
    /** accent colour 1 */
    private Color accent1;
    /** accent colour 2 */
    private Color accent2;
    /** pathname of background image */
    private String pathname;

    /**
     * Coming Soon constructor. Creates a new coming soon page.
     * Takes in all the design specifications that vary between coming soon pages
     * @param display the display object
     * @param location the name of the location of the screen
     * @param subtext subtext underneath the title
     * @param accent1 first accent colour
     * @param accent2 second accent colour
     * @param pathname path of background
     * @throws FileNotFoundException if the background image is not found
     */
    public ComingSoon(Display display, String location, String subtext, Color accent1, Color accent2, String pathname) throws FileNotFoundException {
        //set variables
        this.display = display;
        this.location = location;
        this.subtext = subtext;
        this.accent1 = accent1;
        this.accent2 = accent2;
        this.pathname = pathname;
        setLayout(null);

        // load the background image
        try {
            background = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            System.out.println("Could not find image");
        }

        //load pixelhugger
        try {
            File pixelHuggerFile = new File("media/PixelHugger.otf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, pixelHuggerFile).deriveFont(Font.PLAIN, 39);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // keyboard shortcut: escape key --> back to main menu
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escapePressed");
        ActionMap actionMap = getActionMap();
        actionMap.put("escapePressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen("MainMap");
            }
        });

        //create buttons and labels
        createActionButtons();
        createLabels();
    }

    /**
     * Creates the labels used in the display of this class
     */
    private void createLabels() {

        // Custom label for the location of the page
        CustomJLabel title = new CustomJLabel(location, -1.6);
        title.setFont(new Font("Helvetica", Font.BOLD, 60));
        title.setOpaque(false);

        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int textWidth = metrics.stringWidth(location);

        //create title
        title.setBounds(77, 78, textWidth, 100);
        title.setLetterSpacing(-3);
        title.setForeground(Color.WHITE);
        add(title);

        //create subtext
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setCursor(null);
        textArea.setFont(pixelFont.deriveFont(Font.PLAIN, 18));
        textArea.append(subtext);
        textArea.setForeground(accent1);
        textArea.setOpaque(false);
        textArea.setBounds(78, 223, 251, 148);
        add(textArea);

        //coming soon... text
        CustomJLabel comingSoon = new CustomJLabel("Coming Soon...", 0);
        comingSoon.setFont(new Font("Helvetica", Font.BOLD, 42));
        comingSoon.setOpaque(false);
        comingSoon.setBounds(233, 423, 298, 64);
        comingSoon.setLetterSpacing(-3);
        comingSoon.setForeground(Color.WHITE);
        add(comingSoon);

        // background pills!
        addRoundedCornerLabel(23,43,textWidth+50,120,textWidth+50,100,90,90,accent1,-1.6,Color.WHITE,0); // title pill
        addRoundedCornerLabel(743, 24, 165, 64, 161, 62, 64, 64, new Color(37, 165, 92), 0, Color.WHITE,0); // back pill
        addRoundedCornerLabel(40, 201, 320, 180, 312, 178, 41, 41, new Color(accent2.getRed(), accent2.getGreen(), accent2.getBlue(), 200), 0, Color.WHITE, 0); // subtext
        addRoundedCornerLabel(207, 411, 330, 70, 326, 67, 57, 57, accent1, 0, Color.WHITE, 2);
    }

    /**
     * Adds a debug code input field to the screen.
     */
    public void ifDebugger() {

        //create debug code text field
        JTextField debugCode = new JTextField();
        debugCode.setText("input debug level");
        debugCode.setBackground(accent1);
        debugCode.setForeground(Color.WHITE);
        debugCode.setFont(pixelFont.deriveFont(0, 36));
        debugCode.setBorder(null); // Remove border
        debugCode.setBounds(68, 550, 464, 69);

        //add focus listener for inputting debug code
        debugCode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (debugCode.getText().equals("input debug level")) {
                    debugCode.setText("");
                    debugCode.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (debugCode.getText().isEmpty()) {
                    debugCode.setForeground(Color.BLACK);
                    debugCode.setText("input debug level");
                }
            }
        });
        debugCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // do whatever debug stuff should be done
                System.out.println("The debug level is " + debugCode.getText());
            }
        });

        //add the debug code text field and repaint
        add(debugCode);
        repaint();
    }

    /**
     * Creates action buttons for the coming soon screen
     */
    private void createActionButtons() {

        //create list of action buttons
        JButton[] actionButtons = {
                createActionButton("back",724,20,194,77, Color.WHITE, new Color(0,0,0,0), new Font("Helvetica", Font.BOLD, 44))
        };

        //add action listeners to action buttons
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
     * This is the helper method for the createActionButtons method above.
     * Creates individual action buttons
     * @param text text of the action button
     * @param x x coordinate of the action button
     * @param y y coordinate of the action button
     * @param width width of the action button
     * @param height height of the action button
     * @param foreground color of the foreground of the action button
     * @param background color of the background of the action button
     * @param font specific font used for the action button
     * @return the action button created with the specifications
     */
    private JButton createActionButton(String text, int x, int y, int width, int height, Color foreground, Color background, Font font) {

        //create and set aspects of buttons according to the params
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setBounds(x, y, width, height);
        button.setBackground(background);
        button.setFont(font);
        button.setForeground(foreground);
        button.setFocusable(false);

        return button;
    }

    /**
     * Creates a rounded corner label to display on the coming soon screen.
     * @param labelX the x coordinate of the label
     * @param labelY the y coordinate of the label
     * @param labelWidth the width of the label
     * @param labelHeight the height of the label
     * @param stickerWidth the width of the sticker
     * @param stickerHeight the height of the sticker
     * @param arcWidth the width of the arc
     * @param arcHeight the height of the arc
     * @param fillColor the color used to fill the label
     * @param degrees the degree of the corners
     * @param borderColor the color of the labels border
     * @param borderWidth the width of the labels border
     */
    private void addRoundedCornerLabel(int labelX, int labelY, int labelWidth, int labelHeight, int stickerWidth, int stickerHeight, int arcWidth, int arcHeight, Color fillColor, double degrees, Color borderColor, int borderWidth) {
        RoundIcon icon = new RoundIcon(stickerWidth, stickerHeight, arcWidth, arcHeight, fillColor, degrees, borderColor, borderWidth);
        JLabel label = new JLabel("", icon, JLabel.LEFT);
        label.setBounds(labelX, labelY, labelWidth + borderWidth, labelHeight + borderWidth); // Adjust bounds to include border width
        add(label);
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
