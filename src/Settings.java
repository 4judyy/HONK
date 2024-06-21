import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * The settings class is responsible for the functionality and creation of the settings screen.
 * <p>
 *     This class sets up the user interface of the settings page.
 *     Allows users to access basic gameplay settings such as:
 *     volume, save game, exit game, new game/reset stats.
 * </p>
 * @author Tyler Charles Inwood
 */
public class Settings extends JPanel {
    /** display object */
    private Display display;
    /** database object */
    private Database database = new Database();
    /** map of user profiles */
    private Map userProfileMap = database.getUserProfileMap();
    /** background image for settings screen */
    private Image settingspng;
    /** custom font */
    private Font pixelFont;
    /** welcome label */
    private CustomJLabel welcome;
    /** screen user was previously on, screen settings was accessed from */
    private String backScreen;
    /** float control to control volume of music */
    public FloatControl volumeControl;


    /**
     * Settings constructor. creates the setting screen UI.
     * creates buttons and functionality of settings
     * @param display display object
     * @param backScreen previous screen user was on
     * @param volumeControl volume control variable
     * @throws FileNotFoundException when background image cannot be found
     */
    public Settings(Display display, String backScreen, FloatControl volumeControl) throws FileNotFoundException {
        // Initialize variables
        this.display = display;
        this.backScreen = backScreen;
        this.volumeControl = volumeControl;
        setLayout(null);

        // Load the background image
        try {
            settingspng = ImageIO.read(new File("media/settingsbackground.png"));
        } catch (IOException e) {
            System.out.println("Could not find image");
        }

        //load pixelhugger
        try {
            File pixelHuggerFile = new File("media/PixelHugger.otf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, pixelHuggerFile).deriveFont(Font.PLAIN, 39);
        } catch (IOException | FontFormatException e) {
            // Handle font loading errors
            e.printStackTrace();
        }

        // create action buttons
        createActionButtons();

        // create volume slider
        JSlider volumeSlider = new JSlider();
        volumeSlider.setMaximum(10);
        volumeSlider.setMinimum(0);
        volumeSlider.setValue(5);
        volumeSlider.setMajorTickSpacing(5); // Major tick every 10 units
        volumeSlider.setMinorTickSpacing(1); // Minor tick every 1 unit
        volumeSlider.setPaintTicks(true); // Display tick marks
        volumeSlider.setPaintLabels(true); // Display numerical labels
        volumeSlider.setBounds(80,400,290,40);

        volumeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                // React to changes continuously while the slider is being adjusted
                display.adjustVolume(source.getValue());
            }
        });
        add(volumeSlider);

        // create labels of buttons
        createLabels();

        // make the escape key exit (same as back button)
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escapePressed");
        ActionMap actionMap = getActionMap();
        actionMap.put("escapePressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen(backScreen);
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

                createActionButton("reset stats",520,255,400,40, Color.WHITE, new Color(0,0,0,0), new Font("Helvetica", Font.BOLD, 44)),
                createActionButton("save game",517,365,400,40, Color.WHITE, new Color(0,0,0,0), new Font("Helvetica", Font.BOLD, 44)),
                createActionButton("exit & save",517,476,400,40, Color.WHITE, new Color(0,0,0,0), new Font("Helvetica", Font.BOLD, 44)),
                createActionButton("back",724,20,194,77, Color.WHITE, new Color(0,0,0,0), new Font("Helvetica", Font.BOLD, 44))

        };

        actionButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startOver();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        actionButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        actionButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAndExit();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        actionButtons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen(backScreen);
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
        CustomJLabel settingsBig = new CustomJLabel("Settings", -1.6);
        settingsBig.setFont(new Font("Helvetica", Font.BOLD, 77));
        settingsBig.setOpaque(false);
        settingsBig.setBounds(88, 76, 300, 100);
        settingsBig.setLetterSpacing(-3);
        settingsBig.setForeground(Color.WHITE);
        add(settingsBig);

        //custom label for volume
        CustomJLabel volume = new CustomJLabel("volume", 0);
        volume.setFont(new Font("Helvetica", Font.BOLD, 42));
        volume.setOpaque(false);
        volume.setBounds(160, 320, 140, 39);
        volume.setLetterSpacing(-3);
        volume.setForeground(Color.WHITE);
        add(volume);

        // CUSTOM WELCOME BACK USER LABEL
        welcome = new CustomJLabel("welcome back!", 0);
        welcome.setFont(pixelFont);
        welcome.setOpaque(false);
        welcome.setBounds(57, 237, 523, 57);
        welcome.setLetterSpacing(-3);
        welcome.setForeground(new Color(0, 136, 182));
        add(welcome);

        addRoundedCornerLabel(23,39,414,160,408,143,90,90,new Color(112,213,246),-1.6,Color.WHITE,0); // Settings label
        addRoundedCornerLabel(42,310,380,165,375,163,44,44,new Color(112,213,246),0,Color.WHITE,0); // Volume Box

        // ACTION buttons
        addRoundedCornerLabel(743, 24, 165, 64, 161, 62, 64, 64, new Color(37, 165, 92), 0, Color.WHITE,0);
        addRoundedCornerLabel(496, 231, 430, 85, 427, 82, 37, 37, new Color(246, 195, 42), 0, Color.WHITE, 0);
        addRoundedCornerLabel(496, 340,  430, 85,427, 82, 37, 37, new Color(94, 203, 141), 0, Color.WHITE, 0);
        addRoundedCornerLabel(496, 450,  430, 85, 427, 82, 37, 37, new Color(224, 117, 117), 0, Color.WHITE, 0);
    }

    /**
     * Method called when save game button is clicked
     * saves current stats to user json file
     */
    private void saveData(){
        database.saveDataToJson();
        update();

        CustomDialog popup = new CustomDialog();
        popup.createPopup("Your progress has been saved!","media/yippeee.png","Saving Game Data", JOptionPane.INFORMATION_MESSAGE, new Color(181, 206, 255));
    }

    /**
     * Method called when exit is clicked.
     * saves game, and the asks confirmation to close game
     * @throws IOException when database throws exception
     */
    private void saveAndExit() throws IOException {
        //save data
        update();

        // Create a JOptionPane with custom options
        Object[] options = {"Yes", "No"};

        // Call method to create Option Popup
        int choice = createOptionPopup(options,"Game Saved\nWould you like to exit the game??","Exit Game", "media/saveandexit.png");

        // Handle user choice
        if (choice == JOptionPane.YES_OPTION) {  //Yes was clicked, exit game
            System.exit(0);
        }
    }

    /**
     * Method called when new game/reset stats button is clicked
     * asks user for confirmation, then resets users stats to zero
     * @throws IOException when database throws exception
     */
    private void startOver() throws IOException {

        // Create a JOptionPane with custom options
        Object[] options = {"Yes", "No"};

        // Call helper function
        int choice = createOptionPopup(options,"Your Previous Stats Will Be \nUnrecoverable: Continue?",  "New Game", "media/dizzy.png");

        // Handle user choice
        if (choice == JOptionPane.YES_OPTION) {  //Yes was clicked, exit game
            // set up user session & grab the userProfile
            UserSession sessionManager = UserSession.getInstance();
            System.out.println(sessionManager);
            UserProfile userProfile = sessionManager.getUserProfile();

            // reset scores to 0
            userProfile.setMiddlesexScore(0);
            userProfile.setNaturalSciencesScore(0);
            userProfile.setTalbotScore(0);
            userProfile.setTotalScore(0);

            // Saving wiped stats to map
            userProfileMap.put(userProfile.getUsername(),userProfile);
            database.saveDataToJson();
            update();

            // Create a popup to let users know their data has been saved
            CustomDialog popup = new CustomDialog();
            popup.createPopup("Your previous game's saved data is deleted","media/uhoh.png","New Game", JOptionPane.ERROR_MESSAGE, new Color(32, 193, 242));
        }
    }

    /**
     * Customizes settings with user's username
     * @param username of current user
     */
    public void onLoginSuccess(String username) {
        // Update welcome label with the username
        welcome.setText("Welcome back, " + username + "!");
        repaint();
    }

    /**
     * Update's teacher view and leaderboard with current score
     */
    private void update() {
        // this way we only grab settings ONCE it was created
        TeacherView teacherView = display.getTeacher();
        try {
            teacherView.updateView();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Leaderboard leaderboard = display.getLeaderboard();
        try {
            leaderboard.updateTopTotalScore();
        } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
        }
    }

    /**
     * Methodo to create custom option panes (popup messages)
     * @param options array of message options
     * @param message text of the popup
     * @param title title of the popup window
     * @param filename filename of background image of popup
     * @return custom popup
     */
    private int createOptionPopup(Object[] options, String message, String title, String filename){

        // Set custom properties for message with UI manager
        UIManager.put("OptionPane.background", new Color(255, 222, 116));
        UIManager.put("Panel.background", new Color(255, 222, 116));
        UIManager.put("OptionPane.messageFont", pixelFont.deriveFont(0, 24));
        UIManager.put("OptionPane.messageForeground", Color.BLACK);
        UIManager.put("OptionPane.buttonFont", pixelFont.deriveFont(0, 22));
        UIManager.put("Button.background", Color.gray);
        UIManager.put("Button.foreground", Color.black);

        // create new image icon
        ImageIcon icon = new ImageIcon(filename);

        int choice = JOptionPane.showOptionDialog(
                null, // Parent component (null for default)
                message,
                title,
                JOptionPane.YES_NO_OPTION, // Option type
                JOptionPane.QUESTION_MESSAGE, // Message type
                icon, // Icon (null for default)
                options, // Custom options
                options[0]); // Default options
        return choice;
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
        g.drawImage(settingspng, 0, 0, getWidth(), getHeight(), this);
    }

}
