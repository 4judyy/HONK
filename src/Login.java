import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.*;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingUtilities;

/**
 * The login class is responsible for the functionality and creation of the login screen.
 * <p>
 *     This class sets up the user interface of the login page.
 *     Verifies and checks if user credentials are correct.
 *     Creates an aesthetically pleasing login with input fields.
 * </p>
 * @author Di Zhou
 */
public class Login extends JPanel {
    /** username input box */
    private JTextField userText;
    /** password box */
    private JPasswordField passText;
    /** button for login */
    private JButton button;
    /** select role combo box */
    private JComboBox roleSelect;
    /** background image for login screen */
    private ImageIcon backgroundImage;
    /** Database of user information */
    Database database = new Database();
    /** Display object */
    private Display display;
    /** Starts a new user session */
    UserSession sessionManager = UserSession.getInstance();
    /** Settings object screen */
    private Settings settings;
    /** Main Map object screen */
    private MainMap mainMap;

    /**
     * Constructs the login page.
     * Constructs the UI, sets up listeners and input boxes for the user.
     * Prepares settings, as well as input fields.
     * @param display object
     * @throws IOException when database throw exception
     * @throws FontFormatException when font format is incorrect
     */
    public Login(Display display) throws IOException, FontFormatException {

        // initialize display and layout
        this.display = display;
        setLayout(null);

        // set background images
        backgroundImage = new ImageIcon("media/background.jpg");

        // create title label
        CustomJLabel title = new CustomJLabel("HONK!", -3);
        title.setFont(new Font("Helvetica", Font.BOLD, 100));
        title.setBounds(130,95, 1000, 290);
        title.setLetterSpacing(-6);
        title.setForeground(new Color(58, 51, 25));
        add(title);

        // create welcome label
        RoundIcon welcomeSticker = new RoundIcon(400, 100, 80, 80, new Color(255, 222, 116), -3, Color.WHITE, 0);

        // Create a JLabel with the RoundIcon
        JLabel welcomeLabel = new JLabel("", welcomeSticker, JLabel.CENTER);
        welcomeLabel.setBounds(75, 55, 400, 200);
        add(welcomeLabel);

        //"welcome to" label
        CustomJLabel welcome = new CustomJLabel("welcome to", 0);
        welcome.setFont(new Font("Helvetica", Font.BOLD, 80));
        welcome.setBounds(75, 40, 600, 100);
        welcome.setLetterSpacing(-6);
        welcome.setForeground(new Color(23, 72, 83));
        add(welcome);

        // load font
        File PixelHugger = new File("media/PixelHugger.otf");
        Font pixel = Font.createFont(Font.TRUETYPE_FONT, PixelHugger);

        //text block creation
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setCursor(null);
        textArea.setFont(pixel.deriveFont(Font.PLAIN, 15));
        textArea.append("(Join Jimmy John, the studious goose of Western University, in HONK!,"
                + "the egg-citing educational game for students!)");
        textArea.setBounds(600, 71, 200, 120);
        textArea.setForeground(new Color(51, 51, 25));
        textArea.setOpaque(false);
        add(textArea);

        // Enter username text box creation
        userText = new JTextField(20);
        userText.setBounds(125, 390, 200, 40);
        userText.setForeground(Color.BLACK);
        userText.setText("username");
        userText.setFont(new Font("Helvetica", Font.BOLD, 40));
        userText.setOpaque(false); // Make it transparent
        userText.setBackground(new Color(255, 222, 116, 0));
        userText.setBorder(BorderFactory.createEmptyBorder());

        userText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userText.getText().equals("username")) {
                    userText.setText("");
                    userText.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (userText.getText().isEmpty()) {
                    userText.setForeground(Color.BLACK);
                    userText.setText("username");
                }
            }
        });
        add(userText);

        // input sticker
        RoundIcon input = new RoundIcon(300, 50, 50, 50, new Color(255, 222, 116), 0, Color.WHITE, 0);
        JLabel inputSticker = new JLabel("", input, JLabel.LEFT);
        inputSticker.setBounds(70, 380, 300, 60);
        add(inputSticker);

        // Enter password text box creation
        passText = new JPasswordField(20);
        passText.setBounds(125, 450, 200, 40);
        passText.setForeground(Color.BLACK);
        passText.setText("password");
        passText.setFont(new Font("Helvetica", Font.BOLD, 40));
        passText.setOpaque(false); // Make it transparent
        passText.setBackground(new Color(255, 222, 116, 0));
        passText.setBorder(BorderFactory.createEmptyBorder());
        passText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passText.getText().equals("password")) {
                    passText.setText("");
                    passText.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passText.getText().isEmpty()) {
                    passText.setForeground(Color.BLACK);
                    passText.setText("username");
                }
            }
        });

        add(passText);

        // input sticker
        RoundIcon pass = new RoundIcon(300, 50, 50, 50, new Color(255, 222, 116), Color.WHITE, 0);
        // Create a JLabel with the RoundIcon
        JLabel passSticker = new JLabel("", pass, JLabel.LEFT);
        passSticker.setBounds(70, 440, 300, 60);
        add(passSticker);

        //Drop down role select
        String[] roles = {"-- Student --", "-- Teacher --", "-- Developer --"};
        roleSelect = new JComboBox(roles);
        roleSelect.setSelectedIndex(0);
        roleSelect.setFont(new Font("Helvetica", Font.PLAIN, 21));
        roleSelect.setBackground(new Color(255, 222, 116));
        roleSelect.setBorder(BorderFactory.createEmptyBorder());
        roleSelect.setBounds(70, 500, 300, 60);

        // Set the background color of the dropdown menu and the preview area
        UIManager.put("ComboBox.background", new Color(255, 222, 116));
        UIManager.put("ComboBox.selectionBackground", new Color(255, 222, 116));

        add(roleSelect);

        // input sticker
        RoundIcon roleButton = new RoundIcon(300, 50, 50, 50, new Color(255, 222, 116), 0, Color.WHITE, 0);
        // Create a JLabel with the RoundIcon
        JLabel roleSticker = new JLabel("", roleButton, JLabel.LEFT);
        roleSticker.setBounds(70, 500, 300, 60);
        add(roleSticker);

        // Login button
        button = new JButton("Login");
        button.setBounds(330, 600, 300, 60);
        button.setFont(new Font("Helvetica", Font.BOLD, 35));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBackground(new Color(37, 165, 92,1));
        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkUser();
            }
        });
        add(button);

        // login sticker
        RoundIcon login = new RoundIcon(300, 50, 50, 50, new Color(37, 165, 92), 0, Color.WHITE, 2);
        // Create a JLabel with the RoundIcon
        JLabel loginSticker = new JLabel("", login, JLabel.LEFT);
        loginSticker.setBounds(330, 600, 1000, 60);
        add(loginSticker);

        // make the enter key mapped to the login in button
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterPressed");
        ActionMap actionMap = getActionMap();
        actionMap.put("enterPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkUser();
            }
        });
    }

    /**
     * This class displays a login error message when there is an error
     * This error can be due to invalid password or role selected
     * @param flag defines which error type it is
     */
    public void displayLoginErrorMessage(int flag) {
        CustomDialog popup = new CustomDialog();
        switch (flag){
            case 1:     //password is wrong

                popup.createPopup("UhOh! Your password was incorrect.","media/uhoh.png","Error Logging In", JOptionPane.ERROR_MESSAGE, new Color(32, 193, 242));
                break;
            case 2:     //role is wrong

                popup.createPopup("UhOh! Wrong role selected","media/uhoh.png","Error Logging In", JOptionPane.ERROR_MESSAGE, new Color(32, 193, 242));
                break;
        }
    }

    /**
     * Creates a popup message when new user is created
     * @param user name of new user
     */
    public void newUserPopup(String user){
        CustomDialog popup = new CustomDialog();
        popup.createPopup("New User Created: "+user,"media/newuser.png","Account Created", JOptionPane.INFORMATION_MESSAGE, new Color(32, 193, 242));
    }

    /**
     * getter method for username entered
     * @return username entered
     */
    public String getUsername() {
        return userText.getText();
    }

    /**
     * getter method for password entered
     * @return password entered
     */
    public String getPassword() {
        return new String(passText.getPassword());
    }

    /**
     * getter method for role selected
     * also trims the selection so that its format is acceptable
     * @return role selected
     */
    public String getRole(){
        String role = (String) roleSelect.getSelectedItem();
        role = role.substring(3, role.length() - 3);
        return role;
    }

    /**
     * This class verifies a user as well as their username and password.
     * Will show next screen if username, password, and role are correct.
     * Will also show next screen if new user is created
     * Displays error based on incorrect user input.
     */
    public void checkUser() {
        ///define variables
        String user = getUsername();
        String password = getPassword();
        String role = getRole();

        // create a swing worker to prevent button freezing
        SwingWorker<String[], Void> worker = new SwingWorker<String[], Void>() {
            @Override
            protected String[] doInBackground() throws Exception {
                return database.searchUser(user, password, role);
            }
            @Override
            protected void done() {
                try {
                    String[] result = get();
                    if (result != null) {   //if username already exists
                        if (result[0].equals(password) && result[1].equals(role)) {
                            // if password and role entered were correct
                            sessionManager.setUserProfile(database.getUserProfileMap().get(user));
                            updateMainMap(database.getUserProfileMap().get(user).getPermission());
                            updateDebug(database.getUserProfileMap().get(user).getPermission());
                            updateSettings(user);
                            display.show("MainMap");
                        } else {    //display error message for incorrect password or role entered
                            if (!result[0].equals(password)) {
                                //incorrect password
                                displayLoginErrorMessage(1);
                            } else {
                                //incorrect role selected
                                displayLoginErrorMessage(2);
                            }
                        }
                    } else {    //if username does not exist, create new user
                        newUserPopup(user);
                        sessionManager.setUserProfile(database.getUserProfileMap().get(user));
                        updateMainMap(database.getUserProfileMap().get(user).getPermission());
                        updateDebug(database.getUserProfileMap().get(user).getPermission());
                        updateSettings(user);
                        display.show("MainMap");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace(); // Handle exception
                }
            }
        };
        // Execute the SwingWorker
        worker.execute();
    }

    /**
     * This class updates the settings from login page
     * Uses username entered to display username in settings
     * @param user username of user
     */
    private void updateSettings(String user) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                settings = display.getSettings();
                settings.onLoginSuccess(user);

            }
        });
    }

    /**
     * This class updates the main map from the login page
     * Changes main map so that students get basic main map
     * Teachers and developers have main map with teacher view button
     * @param permission of user that is logging in
     */
    private void updateMainMap(String permission) {
        //checks permissions of user
        if (permission.equals("Teacher") || permission.equals("Developer")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mainMap = display.getMainMap();
                    // calls main map and adds teacher view button
                    mainMap.ifTeacher();
                }
            });
        }
    }

    /**
     * this class updates the debugging modes from the login page
     * adjusts the UI display so that only debuggers can see debug options
     * @param permission of user logging in
     */
    private void updateDebug(String permission) {
        if (permission.equals("Developer")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ComingSoon talbot = display.getTalbot();
                    ComingSoon naturalSci = display.getNaturalSci();
                    Middlesex middlesex = display.getMiddlesex();
                    // adds debug modes for screens
                    talbot.ifDebugger();
                    naturalSci.ifDebugger();
                    middlesex.ifDebugger();
                }
            });
        }
    }

    /**
     * used to paint the background image
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}