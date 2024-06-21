import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * The teacher view class is responsible for the functionality and creation of the teacher view screen.
 * <p>
 *     This class sets up the user interface of the teacher view page.
 *     Allows for teachers to view students scores and progression,
 *     as well as search for a specific student.
 * </p>
 * @author Di Zhou
 */
public class TeacherView extends JPanel {
    /** display object */
    private Display display;
    /** database object */
    private Database database = new Database();
    /** background image for teacher view */
    private Image teacherpng;
    /** custom font */
    private Font pixelFont;
    /** sorted list of user profiles */
    private java.util.List<UserProfile> sortedList;
    /** main table of the UI */
    private JTable table;

    /**
     * teacher view constructor.
     * creates the UI components of the screen,
     * and adds the functionality
     * @param display display object
     * @throws FileNotFoundException when background image cannot be found
     */
    public TeacherView(Display display) throws FileNotFoundException {
        // initialize variables
        this.display = display;
        setLayout(null);

        // load the background image
        try {
            teacherpng = ImageIO.read(new File("media/teacherdashboard.jpg"));
        } catch (IOException e) {
            System.out.println("Could not find image");
        }

        //load pixelhugger
        try {
            File pixelHuggerFile = new File("media/PixelHugger.otf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, pixelHuggerFile).deriveFont(Font.PLAIN, 42);
        } catch (IOException | FontFormatException e) {
            // Handle font loading errors
            e.printStackTrace();
        }

        // create UI elements
        createActionButtons();
        createSearch();
        createLabels();

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
     * Creates search UI and functionality,
     * uses helper method sortAlphabetically
     */
    private void createSearch() {

        sortedList = sortAlphabetically(database.getUserProfileMap());
        DefaultTableModel studentTable = new DefaultTableModel(new Object[]{"Username", "Total Score", "Middlesex Progress"}, 0);

        for (UserProfile student : sortedList) {
            Object[] rowData = {student.getUsername(), student.getTotalScore(), returnDifficulty(student.getMiddlesexScore())};
            studentTable.addRow(rowData);
        }

        table = new JTable(studentTable);

        // set the table column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(244);
        table.getColumnModel().getColumn(1).setPreferredWidth(211);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);

        // set height & width of overall
        table.setSize(800, 300);
        table.setRowHeight(30);

        table.setFont(pixelFont.deriveFont(0, 22));
        table.setForeground(Color.white);

        // some styling
        table.setShowGrid(false);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setBackground(new Color(1, 98, 195));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(pixelFont.deriveFont(Font.BOLD, 24));
        tableHeader.setForeground(Color.white);
        tableHeader.setBackground(new Color(0,0,0,0));
        tableHeader.setBorder(BorderFactory.createEmptyBorder());

        // make it scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        scrollPane.setBounds(84, 294, 780, 290);
        scrollPane.setBackground(new Color(1, 98, 195));

        JTextField searchField = new JTextField(10);
        searchField.setText("search up a student");
        searchField.setFont(pixelFont.deriveFont(0, 30));
        searchField.setOpaque(false);
        searchField.setForeground(Color.white);
        searchField.setBorder(null);
        searchField.setBounds(106, 189, 596, 76);

        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("search up a student")) {
                    searchField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("search up a student");
                }
            }
        });

        ImageIcon search = new ImageIcon("media/search.png");
        Image iconImage = search.getImage();
        Image scaledIcon = iconImage.getScaledInstance(34, 34, Image.SCALE_SMOOTH);
        JButton searchButton = new JButton();
        searchButton.setBounds(821, 210, 34, 34);

        // Make button content area transparent
        searchButton.setContentAreaFilled(false);
        searchButton.setBorderPainted(false); // Remove border

        searchButton.setIcon(new ImageIcon(scaledIcon));


        searchButton.addActionListener(e -> {
            final String searchText = searchField.getText();
            studentTable.setRowCount(0); // Clear the table by setting row count to 0
            if (searchText.isEmpty() || searchText.equalsIgnoreCase( "search up a student")) {
                // If the search text field is empty, display all values
                for (UserProfile student : sortedList) {
                    Object[] rowData = {student.getUsername(), student.getTotalScore(), returnDifficulty(student.getMiddlesexScore())};
                    studentTable.addRow(rowData);
                }
            } else {
                // Otherwise, only add elements that match the search text
                for (UserProfile student : sortedList) {
                    if (student.getUsername().toLowerCase().contains(searchText)) {
                        Object[] rowData = {student.getUsername(), student.getTotalScore(), returnDifficulty(student.getMiddlesexScore())};
                        studentTable.addRow(rowData);
                    }
                }
            }
        });

        // make the enter key mapped to the login in button
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterPressed");
        ActionMap actionMap = getActionMap();
        actionMap.put("enterPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String searchText = searchField.getText();
                studentTable.setRowCount(0); // Clear the table by setting row count to 0
                if (searchText.isEmpty() || searchText.equalsIgnoreCase( "search up a student")) {
                    // If the search text field is empty, display all values
                    for (UserProfile student : sortedList) {
                        Object[] rowData = {student.getUsername(), student.getTotalScore(), returnDifficulty(student.getMiddlesexScore())};
                        studentTable.addRow(rowData);
                    }
                } else {
                    // Otherwise, only add elements that match the search text
                    for (UserProfile student : sortedList) {
                        if (student.getUsername().toLowerCase().contains(searchText)) {
                            Object[] rowData = {student.getUsername(), student.getTotalScore(), returnDifficulty(student.getMiddlesexScore())};
                            studentTable.addRow(rowData);
                        }
                    }
                }
            }
        });


        add(scrollPane);
        add(searchButton);
        add(searchField);
    }

    /**
     * method to update the table when scores are updated
     * @throws FileNotFoundException when database throws exception
     */
    public void updateView() throws FileNotFoundException {

        // Clear existing components from the panel
        removeAll();

        database = new Database();
        // Recreate the sorted list based on updated data
        sortedList = sortAlphabetically(database.getUserProfileMap());

        // Recreate and add all components again
        createActionButtons();
        createSearch();
        createLabels();

        repaint();
    }

    /**
     * Method sorts all users alphabetically
     * @param userProfileMap profile map of the users
     * @return a list of users in alphabetical order
     */
    public List<UserProfile> sortAlphabetically(Map<String, UserProfile> userProfileMap) {
        // Initialize a new TreeMap with the provided map and case-insensitive comparator
        Map<String, UserProfile> reorderedUserMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        reorderedUserMap.putAll(userProfileMap);

        // Initialize a new ArrayList to store sorted UserProfile objects
        List<UserProfile> sortedList = new ArrayList<>();

        // Iterate through the entries of the TreeMap and add each UserProfile to the ArrayList
        for (Map.Entry<String, UserProfile> entry : reorderedUserMap.entrySet()) {
            sortedList.add(entry.getValue());
        }

        // Return the sorted list of user profiles
        return sortedList;
    }

    /**
     * Method to return the difficulty/level of the user,
     * determined by their score
     * @param score users current score
     * @return difficulty level user is at
     */
    private String returnDifficulty(int score) {
        if (score >= 1000) { return "Hard";}
        else if (score >= 500) {return "Medium";}
        return "Easy";
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
     * Method to create labels
     */
    private void createLabels() {

        // Custom label for Settings
        CustomJLabel title = new CustomJLabel("Teacher View", -1.6);
        title.setFont(new Font("Helvetica", Font.BOLD, 77));

        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int height = metrics.getHeight();

        int textWidth = metrics.stringWidth("Teacher View:");
        title.setOpaque(false);
        title.setBounds(73, 55, textWidth, height);
        title.setLetterSpacing(-3);
        title.setForeground(Color.WHITE);
        add(title);


        // background stuff
        addRoundedCornerLabel(25, 38, textWidth+60, height+20, textWidth+50, height+10, 90, 90, new Color(1,98,195), -1.6, Color.WHITE, 0);

        // ACTION buttons
        addRoundedCornerLabel(743, 24, 165, 64, 161, 62, 64, 64, new Color(37, 165, 92), 0, Color.WHITE,0);
        addRoundedCornerLabel(69, 191, 830, 80, 822, 76, 50, 50, new Color(1,98,195), 0, Color.WHITE, 0);
        addRoundedCornerLabel(69, 289, 830, 310, 822, 307, 50, 50, new Color(1,98,195), 0, Color.WHITE, 0);

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
        g.drawImage(teacherpng, 0, 0, getWidth(), getHeight(), this);
    }
}