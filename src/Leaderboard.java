import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;

/**
 *Display a leaderboard that shows the top total scores of players in the game.
 * <p>
 *     Retrieves players data through the database and displays players scored in a ranked list.
 *     Leaderboard is updated each time a user plays a game.
 * </p>
 * @author Tyler Inwood
 */
public class Leaderboard extends JPanel {
    /** display object */
    private final Display display;
    /** database user profile */
    Database database = new Database();
    /** background image for leaderboard screen*/
    private Image leaderboard;
    /** font for the leaderboard text */
    private Font pixelFont;
    /** List containing the top total scores of all users */
    List<UserProfile> topTotalScore = database.topUsers("TotalScore");

    /**
     * Constructor, creates the UI of leaderboard screen.
     * Initializes the leaderboard background, layout and font.
     * @param display object used to create screen
     * @throws FileNotFoundException when background image cannot be found
     */
    public Leaderboard(Display display) throws FileNotFoundException {
        this.display = display;
        setLayout(null);

        // Load the background image
        try {
            leaderboard = ImageIO.read(new File("media/leaderboard.png"));
        } catch (IOException e) {
            System.out.println("Could not find image");
        }

        //load pixelhugger
        try {
            File pixelHuggerFile = new File("media/PixelHugger.otf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, pixelHuggerFile).deriveFont(Font.PLAIN, 18);
        } catch (IOException | FontFormatException e) {
            // Handle font loading errors
            e.printStackTrace();
        }

        // create UI elements
        createPodium();
        createList();
        createLabels();

        // add the description text :D
        JTextArea desc = new JTextArea();
        basicTextSettings(desc);
        desc.setFont(pixelFont.deriveFont(0, 16));
        desc.setBounds(61,207,296,232);
        desc.setForeground(Color.BLACK);
        desc.append("Ready to spread your wings and soar to the top of our leaderboard? Remember, every practice session " +
                "is a step closer to laying the golden egg of success. Let's HONK! our way to the top together.");
        add(desc);

        // make the escape key exit (same as back to game button)
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
     * Creates a podium UI
     */
    private void createPodium() {

        createPodiumText(558, 272, 0);
        createPodiumText(388, 300, 1);
        createPodiumText(738, 326, 2);
    }

    /**
     * method to create podium text for top 3 users
     * @param x coordinate of podium text
     * @param y coordinate of podium text
     * @param i element of list of the top 3 users
     */
    private void createPodiumText(int x, int y, int i) {

        // find the user at position i & see if they exist
        List<Object> winner = findUser(i);

        // would either grab name or the score
        String name = (String) winner.get(0);
        String score = winner.get(1).toString();

        // generate text box's generic wrapping & styling
        JTextArea nameBox = new JTextArea();
        basicTextSettings(nameBox);
        nameBox.setFont(new Font("Helvetica", Font.BOLD, 28));
        nameBox.setBounds(x,y, 190, 76);
        nameBox.append(name);
        nameBox.setForeground(Color.WHITE);
        add(nameBox);

        // create text area
        JTextArea scoreBox = new JTextArea();
        basicTextSettings(scoreBox);
        scoreBox.setFont(pixelFont.deriveFont(0, 17));
        scoreBox.setBounds(x, y + 30, 190, 76);
        scoreBox.append("total score: " + score);
        scoreBox.setForeground(Color.WHITE);
        add(scoreBox);
    }

    /**
     * Creates a list that will hold the top users
     */
    private void createList() {

        int yGap = 40;
        int secondYGap = 40;

        // starting y position
        int yBoundStart = 512;
        int secondYBoundStart = 510;

        // loop through the rest of the top 7
        for (int i = 3; i < 7; i++) {
            createListPodiumText(412, yBoundStart, 707, secondYBoundStart, i);

            yBoundStart += yGap;
            secondYBoundStart += secondYGap;
        }
    }

    /**
     * this method is to create the rest of the users (not podium) text group (text no background)
     * @param x coordinate of the text
     * @param y coordinate of the text
     * @param x2 coordinate of the other text
     * @param y2 coordinate of the other text
     * @param i rank of the user (placement within the top 7)
     */
    private void createListPodiumText(int x, int y, int x2, int y2, int i) {

        // find the user at position i & see if they exist
        List<Object> winner = findUser(i);

        // would either grab name or the score
        String name = (String) winner.get(0);
        String score = winner.get(1).toString();

        // generate text box's generic wrapping & styling
        JTextArea nameBox = new JTextArea();
        basicTextSettings(nameBox);
        nameBox.setFont(new Font("Helvetica", Font.BOLD, 26));
        nameBox.setBounds(x,y, 311, 76);
        nameBox.append(i+1 + ".  " + name);
        nameBox.setForeground(Color.WHITE);
        add(nameBox);

        // create score box
        JTextArea scoreBox = new JTextArea();
        basicTextSettings(scoreBox);
        scoreBox.setFont(pixelFont);
        scoreBox.setBounds(x2, y2, 180, 58);
        scoreBox.append("total score: " + score);
        scoreBox.setForeground(Color.WHITE);
        add(scoreBox);
    }

    /**
     * Method to create labels
     */
    private void createLabels() {

        // custom label for settings
        CustomJLabel leaderboardText = new CustomJLabel("Leaderboard", -1.6);
        leaderboardText.setFont(new Font("Helvetica", Font.BOLD, 77));
        leaderboardText.setOpaque(false);
        leaderboardText.setBounds(61, 76, 457, 121);
        leaderboardText.setLetterSpacing(-3);
        leaderboardText.setForeground(Color.WHITE);
        add(leaderboardText);

        // create back button
        JButton button = new JButton("back");
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setBounds(724, 20, 194, 77);
        button.setBackground(new Color(0,0,0,0));
        button.setFont(new Font("Helvetica", Font.BOLD, 44));
        button.setForeground(Color.WHITE);

        // add action listener for back button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen("MainMap");
            }
        });
        add(button);

        // add rounded corner labels for texts
        addRoundedCornerLabel(23,30,550,160,509,143,90,90,new Color(130,84,205),-1.6,Color.WHITE,0); // leaderboard label
        addRoundedCornerLabel(743, 24, 165, 64, 161, 62, 64, 64, new Color(37, 165, 92), 0, Color.WHITE,0);
        addRoundedCornerLabel( 544, 111,200,360,185,353,35,35,new Color(167,114,242),0,Color.WHITE, 2); // first place podium
        addRoundedCornerLabel(379, 161,214,310,194,303,35,35,new Color(195,159,245),0,Color.WHITE, 2); // second place podium
        addRoundedCornerLabel(687, 217,214,260,207,239,35,35,new Color(219,199,248),0,Color.WHITE, 2); // third place podium
        addRoundedCornerLabel(379, 432,520,300,515,200,28,28,new Color(130,84,205),0,Color.WHITE, 2); // remaining list
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
     * Updates the top total scores on the leaderboard
     * @throws FileNotFoundException for database
     */
    public void updateTopTotalScore() throws FileNotFoundException {
        // remove all previous UI elements
        removeAll();

        // create new database object
        database = new Database();
        topTotalScore = database.topUsers("TotalScore");

        //create podium UI elements
        createPodium();
        createList();
        createLabels();

        // add the podium description text
        JTextArea desc = new JTextArea();
        basicTextSettings(desc);
        desc.setFont(pixelFont.deriveFont(0, 16));
        desc.setBounds(61,207,296,232);
        desc.setForeground(Color.BLACK);
        desc.append("Ready to spread your wings and soar to the top of our leaderboard? Remember, every practice session " +
                "is a step closer to laying the golden egg of success. Let's HONK! our way to the top together.");
        add(desc);

        // make the escape key exit (same as back to game button)
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escapePressed");
        ActionMap actionMap = getActionMap();
        actionMap.put("escapePressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen("MainMap");
            }
        });

        repaint();
    }

    /**
     * Method to set basic text settings
     * @param box basic box UI element
     */
    private void basicTextSettings(JTextArea box) {
        box.setLineWrap(true);
        box.setWrapStyleWord(true);
        box.setEditable(false);
        box.setCursor(null);
        box.setAlignmentX(CENTER_ALIGNMENT);
        box.setAlignmentY(CENTER_ALIGNMENT);
        box.setOpaque(false);
    }

    /**
     * Finds and returns information given a user rank
     * This information contains the username as well as their total score
     * @param i rank of user (for the top 3)
     * @return list with username and score
     * @author Di Zhou
     */

    private List<Object> findUser(int i) {
        List<Object> winnerInfo = new ArrayList<>();
        if (i >= 0 && i < topTotalScore.size()) {
            UserProfile winner = topTotalScore.get(i);
            winnerInfo.add(winner.getUsername());
            winnerInfo.add(winner.getTotalScore());
            return winnerInfo;
        } else {
            // If user doesn't exist, return a list with placeholder values
            List<Object> emptyInfo = new ArrayList<>();
            emptyInfo.add("No User");   // Placeholder for username
            emptyInfo.add(0);           // Placeholder for total score
            return emptyInfo;
        }
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
        g.drawImage(leaderboard, 0, 0, getWidth(), getHeight(), this);
    }
}