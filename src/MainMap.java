import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Map;
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
public class MainMap extends JPanel {
    /** display object */
    private final Display display;
    /** database object for user information */
    private Database database = new Database();
    /** userProfile map to parse users */
    private Map userProfileMap = database.getUserProfileMap();
    /** background image object */
    private Image backgroundImage;
    /** image object for duck */
    private Image duckImage;
    /** font object for custom font */
    private Font pixelFont;

    /**
     * Main Map Constructor. Creates UI display of main map
     * Uses helper methods to create all UI elements,
     * as well ass buttons to navigate throught the game.
     * @param display object
     * @throws IOException for database errors
     * @throws FontFormatException for font formatting errors
     */
    public MainMap(Display display) throws IOException, FontFormatException {
        //initialize variables
        this.display = display;
        setLayout(null);

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("media/main_map.png"));
            duckImage = ImageIO.read(new File("media/mapduck.png"));
        } catch (IOException e) {
            System.out.println("Could not find image");
        }

        //load pixelhugger font
        try {
            File pixelHuggerFile = new File("media/PixelHugger.otf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, pixelHuggerFile).deriveFont(Font.PLAIN, 18);
        } catch (IOException | FontFormatException e) {
            // Handle font loading errors
            e.printStackTrace();
        }

        //call helper methods to create UI elements
        createButtons();
        createIconButtons();
        createActionButtons();
        createTextAreas();
        createLabels();
    }

    /**
     * create buttons helper method
     * creates an array of buttons and adds them,
     * uses create button helper method
     */
    private void createButtons() {
        // Buttons with common attributes
        JButton[] buttons = {
                createButton("NaturalScience", "Play!", 320, 100, 100, 30, Color.WHITE, new Color(186, 117, 255, 0), new Font("Helvetica", Font.BOLD, 18)),
                createButton("Middlesex", "Play!", 570, 245, 100, 30, Color.WHITE, new Color(186, 117, 255, 0), new Font("Helvetica", Font.BOLD, 18)),
                createButton("Talbot", "Play!", 550, 570, 100, 30, Color.WHITE, new Color(186, 117, 255, 0), new Font("Helvetica", Font.BOLD, 18)),
                createButton("Tutorial", "View Tutorial", 210, 620, 200, 30, Color.BLACK, new Color(0,0,0,0),  pixelFont),
        };
        // Add buttons to the panel
        for (JButton button : buttons) {
            add(button);
        }
    }

    /**
     * Create button method
     * given specific parameters, creates unique buttons
     * @param screenName name of screen shown when button is clicked
     * @param text text of button
     * @param x coordinate of button
     * @param y coordinate of button
     * @param width of button
     * @param height of button
     * @param foreground color of button foreground
     * @param background color of button background
     * @param font of button
     * @return unique button created from parameters
     */
    private JButton createButton(String screenName, String text, int x, int y, int width, int height, Color foreground, Color background, Font font) {
        // create button using params
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setBounds(x, y, width, height);
        button.setBackground(background);
        button.setFont(font);
        button.setForeground(foreground);
        button.setFocusable(false);

        // add ActionListener to button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen(screenName);
            }
        });
        return button;
    }

    /**
     * Creates buttons with icons helper method
     * uses createIconButton helper method
     */
    private void createIconButtons() {
        // create array of icon buttons
        JButton[] buttons = {
                createIconButton("Settings", "Settings", 32, 20, 150, 30, Color.BLACK, new Color(186, 117, 255, 0), new Font("Helvetica", Font.BOLD, 18), "media/setting_icon.png"),
                createIconButton("Leaderboard", "Leaderboard", 750, 20, 170, 30, Color.BLACK, new Color(0, 0, 0, 0), new Font("Helvetica", Font.BOLD, 18), "media/trophy_icon.png")
        };
        //loop through array and add them to the screen
        for (JButton button : buttons) {
            add(button);
        }
    }

    /**
     * Creates buttons with icons
     * @param screenName name of screen shown when button is clicked
     * @param text title of button
     * @param x coordinate of button
     * @param y coordinate of button
     * @param width of button
     * @param height of button
     * @param foreground color of button foreground
     * @param background color of button background
     * @param font font of button
     * @param iconPath path of set icon
     * @return unique button created from parameters
     */
    private JButton createIconButton(String screenName, String text, int x, int y, int width, int height, Color foreground, Color background, Font font, String iconPath) {

        // create button
        JButton button = createButton(screenName, text, x, y, width, height, foreground, background, font);

        // set icon
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(iconPath);
            Image iconImage = icon.getImage();
            Image scaledIcon = iconImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledIcon));
        }
        return button;
    }

    /**
     * Creates action buttons helper method
     * uses create actions buttons helper method
     */
    private void createActionButtons() {

        // Action buttons with common attributes
        JButton[] actionButtons = {
                createActionButton("Continue", 700, 501, 200, 30, Color.WHITE, Color.BLUE, new Font("Helvetica", Font.BOLD, 24)),
                createActionButton("New Game", 700, 551, 200, 30, Color.WHITE, Color.GREEN, new Font("Helvetica", Font.BOLD, 24)),
                createActionButton("Exit", 700, 601, 200, 30, Color.WHITE, Color.RED, new Font("Helvetica", Font.BOLD, 24)),

        };

        //set button action listeners
        actionButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continueButton();
            }
        });
        actionButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startOver();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
        // set button components with given parameters
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
     * Create text areas used in main map UI
     */
    private void createTextAreas() {

        // JTextArea for intro text
        JTextArea introText = new JTextArea();
        introText.setFont(pixelFont.deriveFont(Font.PLAIN, 13));
        introText.setLineWrap(true);
        introText.setWrapStyleWord(true);
        introText.setEditable(false);
        introText.setOpaque(false);
        introText.setCursor(null);
        introText.append("Welcome to HONK! Throughout this game, your skills will be put to the ultimate test across a multitude of thrilling mini-games! Your goal is to accumulate the most points by conquering these diverse challenges. It's time to show what you're made of!\n\n");
        introText.append("Created in CS 2212 Winter 2024, Western University.\n");
        introText.append("Group 33: Judy Zhou, Tyler Inwood, Matthew Yu, Marissa Wang, Stephen Bian");
        introText.setBounds(55, 420, 350, 375);
        add(introText);

    }

    /**
     * Method to create labels
     */
    private void createLabels() {
        // Label for banner title
        JLabel bannerTitle = new JLabel("HONK!'s Main Menu");
        bannerTitle.setFont(new Font("Helvetica", Font.BOLD, 24));
        bannerTitle.setBounds(365, 7, 260, 60);
        add(bannerTitle);

        // CustomJLabel for Jimmy's Trusty Map
        CustomJLabel jimmyMap = new CustomJLabel("Jimmy's Trusty Map", -1.6);
        jimmyMap.setFont(new Font("Helvetica", Font.BOLD, 42));
        jimmyMap.setOpaque(false);
        jimmyMap.setBounds(525, 113, 1000, 200);
        jimmyMap.setLetterSpacing(-3);
        jimmyMap.setForeground(new Color(58, 51, 25));
        add(jimmyMap);

        // add the background labels
        addRoundedCornerLabel(30, 10, 900, 50, 900, 50, 30, 30, new Color(255, 225, 135), 0, Color.BLACK, 0); // Top banner
        addRoundedCornerLabel(500, 65, 450, 150, 400, 92, 90, 90, new Color(255, 225, 135), -1.65, Color.BLACK, 0); // Jimmy's map
        addRoundedCornerLabel(40, 200, 600, 600, 370, 200, 100, 100, new Color(219, 199, 248), 0, Color.BLACK, 2); // Intro text sticker
        addRoundedCornerLabel(210, 610,  300, 50, 200, 40, 30, 30, new Color(219, 199, 248),0,Color.BLACK, 2); // View Tutorial

        // PLAY buttons
        addRoundedCornerLabel(320, 100,  200, 30, 100, 30, 30, 30, new Color(167, 114, 242), 0, Color.WHITE, 0); // NatSci
        addRoundedCornerLabel(570, 245,  200, 30, 100, 30, 30, 30, new Color(167, 114, 242), 0, Color.WHITE, 0); // Middlesex
        addRoundedCornerLabel(550, 570,  200, 30, 100, 30, 30, 30, new Color(167, 114, 242), 0, Color.WHITE, 0); // Talbot

        // ACTION buttons
        addRoundedCornerLabel(700, 495, 300, 40, 200, 40, 40, 40, new Color(223, 190, 92), 0, Color.WHITE, 0);
        addRoundedCornerLabel(700, 545,  300, 40, 200, 40, 40, 40, new Color(94, 203, 141), 0, Color.WHITE, 0);
        addRoundedCornerLabel(700, 595,  300, 40, 200, 40, 40, 40, new Color(224, 117, 117), 0, Color.WHITE, 0);
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
     * ifTeacher method to determine if teacher view button should appear
     * called in check user, add teacher view button to main map
     */
    public void ifTeacher() {
        JButton teacherButton = createActionButton("Teacher View", 700, 451, 200, 30, Color.WHITE, Color.RED, new Font("Helvetica", Font.BOLD, 24));
        teacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.show("Teacher");
            }
        });
        add(teacherButton);
        addRoundedCornerLabel(700, 445, 300, 40, 200, 40, 40, 40, new Color(112, 212, 255), 0, Color.WHITE, 0);

        repaint();
    }

    /**
     * Method for creating popup when continue button is pressed
     * Shows users current stats
     */
    public void continueButton(){
        //popup screen showing current game stats
        UserSession sessionManager = UserSession.getInstance();
        UserProfile userProfile = sessionManager.getUserProfile();
        String message = "Username: " + userProfile.getUsername() + "\nTotal Score: " + userProfile.getTotalScore() + "\nMiddlesex Score: " + userProfile.getMiddlesexScore();
        CustomDialog popup = new CustomDialog();
        popup.createPopup(message,"media/yippeee.png","Saving Game Data", JOptionPane.INFORMATION_MESSAGE, new Color(181, 206, 255));
    }

    /**
     * Method for process work when NEW GAME button is pressed
     * asks for confirmation, then resets stats and popup informs user
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
     * Method for process work when SAVE AND EXIT button is pressed
     * asks for confirmation, saves the game and exits
     */
    private void saveAndExit() throws IOException {
        // Save & update the data
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
     * Method to update teacher view and leaderboard,
     * when users stats are changed
     */
    private void update() {
        //update teacher view
        TeacherView teacherView = display.getTeacher();
        try {
            teacherView.updateView();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //update leaderboard
        Leaderboard leaderboard = display.getLeaderboard();
        try {
            leaderboard.updateTopTotalScore();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create Option Popup Method.
     * Uses JOptionalPanel's showOptionDialog method to display options for used
     * Called to create a popup box with customized messages.
     * @param options the user has
     * @param message the message the popup will display
     * @param title the title of the popup box
     * @param filename of the image
     */
    private int createOptionPopup(Object[] options, String message, String title, String filename) throws IOException {

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
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image at (0, 0) with the same size as the panel
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(duckImage, 60, 10, duckImage.getWidth(this) /2 , duckImage.getHeight(this) /2, this);
    }
}